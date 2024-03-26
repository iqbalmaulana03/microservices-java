package com.iqbal.inventoryservice.controller;

import com.iqbal.inventoryservice.model.InventoryResponse;
import com.iqbal.inventoryservice.model.WebResponse;
import com.iqbal.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService service;

    @GetMapping("/{sku-code}")
    ResponseEntity<WebResponse<InventoryResponse>> isInStock(@PathVariable("sku-code") String skuCode){
        InventoryResponse inStock = service.isInStock(skuCode);

        WebResponse<InventoryResponse> response = WebResponse.<InventoryResponse>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("successfully get data by sku code")
                .data(inStock)
                .build();

        return ResponseEntity.ok(response);
    }
}
