package com.iqbal.productservice.service.impl;

import com.iqbal.productservice.entity.Product;
import com.iqbal.productservice.model.request.ProductRequest;
import com.iqbal.productservice.model.response.ProductResponse;
import com.iqbal.productservice.repository.ProductRepository;
import com.iqbal.productservice.utils.ValidationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository repository;

    @Mock
    private ValidationUtils utils;

    @Test
    void create() {
        ProductRequest request = ProductRequest.builder()
                .name("Iphone 15")
                .description("iphone 15")
                .price(BigDecimal.valueOf(2000))
                .build();

        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .build();

        when(repository.saveAndFlush(any(Product.class))).thenReturn(product);

        ProductResponse productResponse = productService.create(request);

        verify(utils).validate(request);

        assertEquals(product.getId(), productResponse.getId());
        assertEquals(product.getName(), productResponse.getName());
        assertEquals(product.getPrice(), productResponse.getPrice());
        assertEquals(product.getDescription(), productResponse.getDescription());
    }

    @Test
    void getAll() {

        List<Product> dummyProducts = new ArrayList<>();
        dummyProducts.add(new Product("1", "Product 1", "product 1", BigDecimal.valueOf(2000)));
        dummyProducts.add(new Product("2", "Product 2", "product 2", BigDecimal.valueOf(1000)));

        when(repository.findAll()).thenReturn(dummyProducts);

        List<ProductResponse> result = productService.getAll();

        assertEquals(dummyProducts.size(), result.size());
        for (int i = 0; i < dummyProducts.size(); i++) {
            assertEquals(dummyProducts.get(i).getId(), result.get(i).getId());
            assertEquals(dummyProducts.get(i).getName(), result.get(i).getName());
        }
    }
}