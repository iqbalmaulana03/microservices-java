package com.iqbal.productservice.controller;

import com.iqbal.productservice.model.request.ProductRequest;
import com.iqbal.productservice.model.response.ProductResponse;
import com.iqbal.productservice.model.response.WebResponse;
import com.iqbal.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    ResponseEntity<WebResponse<ProductResponse>> create(@RequestBody ProductRequest request){
        ProductResponse productResponse = service.create(request);

        WebResponse<ProductResponse> response = WebResponse.<ProductResponse>builder()
                .message("successfully create product")
                .status(HttpStatus.CREATED.getReasonPhrase())
                .data(productResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    ResponseEntity<WebResponse<List<ProductResponse>>> getAll(){
        List<ProductResponse> all = service.getAll();

        WebResponse<List<ProductResponse>> response = WebResponse.<List<ProductResponse>>builder()
                .message("successfully get all product")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(all)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}")
    ResponseEntity<WebResponse<String>> get(@PathVariable("productId") String id){
        String productResponse = service.get(id);

        WebResponse<String> response = WebResponse.<String>builder()
                .message("successfully get product by id")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(productResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}
