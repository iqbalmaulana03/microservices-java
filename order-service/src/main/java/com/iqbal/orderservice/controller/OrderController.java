package com.iqbal.orderservice.controller;

import com.iqbal.orderservice.model.request.OrderRequest;
import com.iqbal.orderservice.model.response.WebResponse;
import com.iqbal.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    @PostMapping
    ResponseEntity<WebResponse<String>> placeOrder(@RequestBody OrderRequest request){
        service.placeOrder(request);

        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("successfully create order")
                .data("Order Placed Successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
