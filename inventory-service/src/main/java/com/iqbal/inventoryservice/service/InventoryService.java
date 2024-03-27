package com.iqbal.inventoryservice.service;

import com.iqbal.inventoryservice.model.request.InventoryRequest;
import com.iqbal.inventoryservice.model.response.InventoryResponse;

import java.util.List;

public interface InventoryService {

    List<InventoryResponse> isInStock(List<String> skuCode);

    InventoryResponse create(InventoryRequest request);
}
