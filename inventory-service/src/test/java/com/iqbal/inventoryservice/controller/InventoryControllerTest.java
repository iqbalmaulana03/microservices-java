package com.iqbal.inventoryservice.controller;

import com.iqbal.inventoryservice.model.InventoryResponse;
import com.iqbal.inventoryservice.model.WebResponse;
import com.iqbal.inventoryservice.service.impl.InventoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryControllerTest {

    @InjectMocks
    private InventoryController controller;

    @Mock
    private InventoryServiceImpl inventoryService;

    @Test
    void isInStock() {

        String skuCode = "Iphone 15";

        InventoryResponse expectedResponse = InventoryResponse.builder()
                .id(1L)
                .skuCode(skuCode)
                .quantity(1)
                .build();

        when(inventoryService.isInStock(skuCode)).thenReturn(expectedResponse);

        ResponseEntity<WebResponse<InventoryResponse>> responseEntity = controller.isInStock(skuCode);

        verify(inventoryService, times(1)).isInStock(skuCode);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        WebResponse<InventoryResponse> responseBody = responseEntity.getBody();
        assertEquals("successfully get data by sku code", Objects.requireNonNull(responseBody).getMessage());
        assertEquals(expectedResponse, responseBody.getData());
    }
}