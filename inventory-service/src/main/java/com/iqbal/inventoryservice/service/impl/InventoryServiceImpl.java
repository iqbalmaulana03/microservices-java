package com.iqbal.inventoryservice.service.impl;

import com.iqbal.inventoryservice.entity.Inventory;
import com.iqbal.inventoryservice.model.request.InventoryRequest;
import com.iqbal.inventoryservice.model.response.InventoryResponse;
import com.iqbal.inventoryservice.repository.InventoryRepository;
import com.iqbal.inventoryservice.service.InventoryService;
import com.iqbal.inventoryservice.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repository;

    private final ValidationUtils utils;

    private final WebClient webClient;

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        List<Inventory> inventories = repository.findBySkuCodeIn(skuCode);

        if (inventories.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory Not Found");

        return inventories.stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()
                ).toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InventoryResponse create(InventoryRequest request) {

        utils.validate(request);

        String product = webClient.get()
                .uri("http://localhost:8080/api/products" + request.getProductId())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Inventory inventory = Inventory.builder()
                .skuCode(request.getSkuCode())
                .quantity(request.getQuantity())
                .productId(product)
                .build();

        repository.save(inventory);

        return InventoryResponse.builder()
                .skuCode(inventory.getSkuCode())
                .isInStock(inventory.getQuantity() > 0)
                .build();
    }
}
