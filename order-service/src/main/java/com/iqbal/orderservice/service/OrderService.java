package com.iqbal.orderservice.service;

import com.iqbal.orderservice.model.request.OrderRequest;

public interface OrderService {

    void placeOrder(OrderRequest request);
}
