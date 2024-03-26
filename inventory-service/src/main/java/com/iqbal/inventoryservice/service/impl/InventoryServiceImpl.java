package com.iqbal.inventoryservice.service.impl;

import com.iqbal.inventoryservice.entity.Inventory;
import com.iqbal.inventoryservice.model.InventoryResponse;
import com.iqbal.inventoryservice.repository.InventoryRepository;
import com.iqbal.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repository;

    @Override
    @Transactional(readOnly = true)
    public InventoryResponse isInStock(String skuCode) {
        Inventory inventory = repository.findBySkuCode(skuCode).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory Not Found")
        );

        return toMapDto(inventory);
    }

    private InventoryResponse toMapDto(Inventory inventory){
        return InventoryResponse.builder()
                .id(inventory.getId())
                .skuCode(inventory.getSkuCode())
                .quantity(inventory.getQuantity())
                .build();
    }
}
