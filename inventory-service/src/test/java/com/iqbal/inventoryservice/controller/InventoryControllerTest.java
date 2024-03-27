package com.iqbal.inventoryservice.controller;

import com.iqbal.inventoryservice.model.request.InventoryRequest;
import com.iqbal.inventoryservice.model.response.InventoryResponse;
import com.iqbal.inventoryservice.service.impl.InventoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryServiceImpl inventoryService;

    @Test
    void isInStock() throws Exception {

        String responseJson = "{\"status\":\"OK\",\"message\":\"successfully get data by sku code\",\"data\":[{\"skuCode\":\"SKU001\",\"inStock\":true},{\"skuCode\":\"SKU002\",\"inStock\":false},{\"skuCode\":\"SKU003\",\"inStock\":true}]}";

        List<InventoryResponse> dummyInventories = Arrays.asList(
                new InventoryResponse("SKU001", true),
                new InventoryResponse("SKU002", false),
                new InventoryResponse("SKU003", true)
        );

        when(inventoryService.isInStock(anyList())).thenReturn(dummyInventories);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory")
                        .param("skuCode", "SKU001", "SKU002", "SKU003"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson));
    }

    @Test
    void create() throws Exception {
        InventoryRequest request = new InventoryRequest("product001", "SKU001", 10);
        InventoryResponse inventoryResponse = new InventoryResponse("SKU001", true);

        when(inventoryService.create(any(InventoryRequest.class))).thenReturn(inventoryResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/inventory")
                        .contentType("application/json")
                        .content("{ \"productId\": \"product001\", \"skuCode\": \"SKU001\", \"quantity\": 10 }"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("successfully create inventory"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.skuCode").value("SKU001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.inStock").value(true));

        verify(inventoryService, times(1)).create(request);
    }
}