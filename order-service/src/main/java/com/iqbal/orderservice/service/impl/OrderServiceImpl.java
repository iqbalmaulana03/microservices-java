package com.iqbal.orderservice.service.impl;

import com.iqbal.orderservice.entity.Order;
import com.iqbal.orderservice.entity.OrderLineItems;
import com.iqbal.orderservice.model.request.OrderLineItemsDto;
import com.iqbal.orderservice.model.request.OrderRequest;
import com.iqbal.orderservice.repository.OrderRepository;
import com.iqbal.orderservice.service.OrderService;
import com.iqbal.orderservice.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    private final ValidationUtils utils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void placeOrder(OrderRequest request) {

        utils.validate(request);

        List<OrderLineItems> orderLineItems = request.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemsList(orderLineItems)
                .build();

        repository.save(order);
    }

    private OrderLineItems mapToDto(OrderLineItemsDto request){
        return OrderLineItems.builder()
                .skuCode(request.getSkuCode())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
    }
}
