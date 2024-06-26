package com.iqbal.orderservice.service.impl;

import com.iqbal.orderservice.entity.Order;
import com.iqbal.orderservice.entity.OrderLineItems;
import com.iqbal.orderservice.event.OrderPlacedEvent;
import com.iqbal.orderservice.model.response.InventoryResponse;
import com.iqbal.orderservice.model.request.OrderLineItemsDto;
import com.iqbal.orderservice.model.request.OrderRequest;
import com.iqbal.orderservice.repository.OrderRepository;
import com.iqbal.orderservice.service.OrderService;
import com.iqbal.orderservice.utils.ValidationUtils;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    private final ValidationUtils utils;

    private final WebClient.Builder webClient;

    private final ObservationRegistry observationRegistry;

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String placeOrder(OrderRequest request) {

        utils.validate(request);

        List<OrderLineItems> orderLineItems = request.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemsList(orderLineItems)
                .build();

        List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

        Observation inventoryServiceObservation = Observation.createNotStarted("inventory-service-lookup",
                this.observationRegistry);

        inventoryServiceObservation.lowCardinalityKeyValue("call", "inventory-service");

        return inventoryServiceObservation.observe(() -> {
            InventoryResponse[] inventoryResponses = webClient.build().get()
                    .uri("http://inventory-service/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            assert inventoryResponses != null;

            boolean result = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

            if (result) {
                repository.save(order);
                kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
                return "Order Placed";
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product is not in stock, please try again latter");
            }
        });
    }

    private OrderLineItems mapToDto(OrderLineItemsDto request){
        return OrderLineItems.builder()
                .skuCode(request.getSkuCode())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
    }
}
