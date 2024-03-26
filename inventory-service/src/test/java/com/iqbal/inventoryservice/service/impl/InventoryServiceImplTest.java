package com.iqbal.inventoryservice.service.impl;

import com.iqbal.inventoryservice.entity.Inventory;
import com.iqbal.inventoryservice.model.InventoryResponse;
import com.iqbal.inventoryservice.repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceImplTest {

    private final InventoryRepository repository = mock(InventoryRepository.class);

    private final InventoryServiceImpl inventoryService = new InventoryServiceImpl(repository);

    @Test
    void isInStock() {

        Inventory inventory = Inventory.builder()
                .id(1L)
                .skuCode("Iphone 15")
                .quantity(12)
                .build();

        when(repository.findBySkuCode(inventory.getSkuCode())).thenReturn(Optional.of(inventory));

        InventoryResponse result = inventoryService.isInStock(inventory.getSkuCode());

        verify(repository).findBySkuCode(inventory.getSkuCode());

        assertNotNull(result);
    }

    @Test
    void isInStock_Not_Found() {

        Inventory inventory = Inventory.builder()
                .id(1L)
                .skuCode("Iphone 15")
                .quantity(12)
                .build();

        when(repository.findBySkuCode(inventory.getSkuCode())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> inventoryService.isInStock(inventory.getSkuCode()));

        // Verify the interaction
        verify(repository).findBySkuCode(inventory.getSkuCode());

        // Assert the exception message and status
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Inventory Not Found", exception.getReason());
    }
}