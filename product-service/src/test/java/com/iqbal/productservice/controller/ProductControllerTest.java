package com.iqbal.productservice.controller;

import com.iqbal.productservice.model.request.ProductRequest;
import com.iqbal.productservice.model.response.ProductResponse;
import com.iqbal.productservice.model.response.WebResponse;
import com.iqbal.productservice.service.impl.ProductServiceImpl;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductServiceImpl productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void create() {

        ProductRequest request = ProductRequest.builder()
                .name("Iphone 15")
                .description("iphone 15")
                .price(BigDecimal.valueOf(2000))
                .build();

        ProductResponse expectedResponse = ProductResponse.builder()
                .name("Iphone 15")
                .description("iphone 15")
                .price(BigDecimal.valueOf(2000))
                .build();

        when(productService.create(request)).thenReturn(expectedResponse);

        ResponseEntity<WebResponse<ProductResponse>> responseEntity = productController.create(request);

        verify(productService, times(1)).create(request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        WebResponse<ProductResponse> responseBody = responseEntity.getBody();
        assertEquals("successfully create product", Objects.requireNonNull(responseBody).getMessage());
        assertEquals(expectedResponse, responseBody.getData());
    }

    @Test
    void getAll() {

        List<ProductResponse> dummyProducts = new ArrayList<>();
        dummyProducts.add(new ProductResponse("1", "Product 1", "product 1", BigDecimal.valueOf(2000)));
        dummyProducts.add(new ProductResponse("2", "Product 2", "product 2", BigDecimal.valueOf(1000)));

        when(productService.getAll()).thenReturn(dummyProducts);

        ResponseEntity<WebResponse<List<ProductResponse>>> responseEntity = productController.getAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        WebResponse<List<ProductResponse>> responseBody = responseEntity.getBody();
        assertEquals("successfully get all product", Objects.requireNonNull(responseBody).getMessage());
        assertEquals(dummyProducts, responseBody.getData());
    }
}