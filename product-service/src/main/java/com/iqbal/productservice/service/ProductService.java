package com.iqbal.productservice.service;

import com.iqbal.productservice.model.request.ProductRequest;
import com.iqbal.productservice.model.response.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse create(ProductRequest request);

    List<ProductResponse> getAll();

    String get(String id);
}
