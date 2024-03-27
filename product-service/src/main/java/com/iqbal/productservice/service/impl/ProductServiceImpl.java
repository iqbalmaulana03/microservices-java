package com.iqbal.productservice.service.impl;

import com.iqbal.productservice.entity.Product;
import com.iqbal.productservice.model.request.ProductRequest;
import com.iqbal.productservice.model.response.ProductResponse;
import com.iqbal.productservice.repository.ProductRepository;
import com.iqbal.productservice.service.ProductService;
import com.iqbal.productservice.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final ValidationUtils utils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductResponse create(ProductRequest request) {

        utils.validate(request);

        Optional<Product> byName = repository.findByName(request.getName());

        if (byName.isPresent()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product Name has be exist");

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();

        repository.saveAndFlush(product);
        log.info("Product {} is saved", product.getId());

        return toProductResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAll() {

        List<Product> all = repository.findAll();

        return all.stream().map(this::toProductResponse).toList();
    }

    @Override
    public String get(String id) {
        Product product = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found")
        );

        return product.getId();
    }

    private ProductResponse toProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
