package com.iqbal.inventoryservice.service.impl;

import com.iqbal.inventoryservice.entity.Inventory;
import com.iqbal.inventoryservice.model.request.InventoryRequest;
import com.iqbal.inventoryservice.model.response.InventoryResponse;
import com.iqbal.inventoryservice.repository.InventoryRepository;
import com.iqbal.inventoryservice.utils.ValidationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.junit.jupiter.api.function.Executable;
import reactor.core.publisher.Mono;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    private InventoryRepository repository;

    @Mock
    private ValidationUtils utils;

    @Mock
    private WebClient webClient;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @Test
    void isInStock() {

        List<String> skuCodes = Arrays.asList("SKU001", "SKU002", "SKU003");
        List<Inventory> dummyInventories = Arrays.asList(
                new Inventory(1L, "SKU001", 10, "123"),
                new Inventory(2L, "SKU002", 0, "123"),
                new Inventory(3L, "SKU003", -5, "123")
        );

        when(repository.findBySkuCodeIn(skuCodes)).thenReturn(dummyInventories);

        List<InventoryResponse> result = inventoryService.isInStock(skuCodes);

        assertEquals(dummyInventories.size(), result.size());
        assertEquals("SKU001", result.get(0).getSkuCode());
        assertTrue(result.get(0).isInStock());
        assertEquals("SKU002", result.get(1).getSkuCode());
        assertFalse(result.get(1).isInStock());
        assertEquals("SKU003", result.get(2).getSkuCode());
        assertFalse(result.get(2).isInStock());
    }

    @Test
    void isInStock_Not_Found() {

        when(repository.findBySkuCodeIn(Collections.singletonList("NonExistingSKU"))).thenReturn(Collections.emptyList());

        Executable executable = () -> inventoryService.isInStock(Collections.singletonList("NonExistingSKU"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, executable);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Inventory Not Found", exception.getReason());
    }

    @Test
    void create() {

        InventoryRequest request = InventoryRequest.builder()
                .productId("product123")
                .skuCode("SKU001")
                .quantity(1)
                .build();

        doNothing().when(utils).validate(request);

        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        Mono<String> monoResponse = Mono.just("Product Information");

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(monoResponse);

        when(repository.save(any(Inventory.class))).thenReturn(new Inventory(1L, "SKU001", 10, "123456"));

        InventoryResponse response = inventoryService.create(request);

        assertNotNull(response);
        assertEquals("SKU001", response.getSkuCode());
        assertTrue(response.isInStock());
        verify(utils, times(1)).validate(request);
        verify(repository, times(1)).save(any(Inventory.class));
    }
}