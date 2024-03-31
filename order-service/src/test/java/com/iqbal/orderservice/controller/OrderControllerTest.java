package com.iqbal.orderservice.controller;

import com.iqbal.orderservice.model.request.OrderLineItemsDto;
import com.iqbal.orderservice.model.request.OrderRequest;
import com.iqbal.orderservice.model.response.WebResponse;
import com.iqbal.orderservice.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderServiceImpl orderService;

    @Test
    void placeOrder() {

//        OrderLineItemsDto orderLineItemsDto = OrderLineItemsDto.builder()
//                .skuCode("Iphone 15")
//                .price(BigDecimal.valueOf(2000))
//                .quantity(1)
//                .build();
//
//        List<OrderLineItemsDto> orderLineItemsDtoList = new ArrayList<>();
//        orderLineItemsDtoList.add(orderLineItemsDto);
//
//        OrderRequest request = new OrderRequest(orderLineItemsDtoList);
//
//        doNothing().when(orderService).placeOrder(request);
//
//        CompletableFuture<String> responseEntity = orderController.placeOrder(request);
//
//        verify(orderService, times(1)).placeOrder(request);
//
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//        WebResponse<String> responseBody = responseEntity.getBody();
//        assertEquals("successfully create order", Objects.requireNonNull(responseBody).getMessage());
//        assertEquals("Order Placed Successfully", responseBody.getData());
    }
}