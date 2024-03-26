package com.iqbal.orderservice.service.impl;

import com.iqbal.orderservice.model.request.OrderLineItemsDto;
import com.iqbal.orderservice.model.request.OrderRequest;
import com.iqbal.orderservice.repository.OrderRepository;
import com.iqbal.orderservice.utils.ValidationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository repository;

    @Mock
    private ValidationUtils utils;

    @Test
    void placeOrder() {

        OrderLineItemsDto orderLineItemsDto = OrderLineItemsDto.builder()
                .skuCode("Iphone 15")
                .price(BigDecimal.valueOf(2000))
                .quantity(1)
                .build();

        List<OrderLineItemsDto> orderLineItemsDtoList = new ArrayList<>();
        orderLineItemsDtoList.add(orderLineItemsDto);

        OrderRequest request = new OrderRequest(orderLineItemsDtoList);

        doNothing().when(utils).validate(request);

        orderService.placeOrder(request);

        verify(repository, times(1)).save(argThat(order ->
                order.getOrderNumber() != null &&
                        order.getOrderLineItemsList().size() == 1 &&
                        order.getOrderLineItemsList().get(0).getSkuCode().equals("Iphone 15") &&
                        order.getOrderLineItemsList().get(0).getPrice().compareTo(BigDecimal.valueOf(2000)) == 0 &&
                        order.getOrderLineItemsList().get(0).getQuantity() == 1
        ));
    }
}