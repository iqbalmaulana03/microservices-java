package com.iqbal.inventoryservice.controller;

import com.iqbal.inventoryservice.model.request.InventoryRequest;
import com.iqbal.inventoryservice.model.response.InventoryResponse;
import com.iqbal.inventoryservice.model.response.WebResponse;
import com.iqbal.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        return service.isInStock(skuCode);
    }

    @PostMapping
    ResponseEntity<WebResponse<InventoryResponse>> create(@RequestBody InventoryRequest request){
        InventoryResponse inventoryResponse = service.create(request);

        WebResponse<InventoryResponse> response = WebResponse.<InventoryResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("successfully create inventory")
                .data(inventoryResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
