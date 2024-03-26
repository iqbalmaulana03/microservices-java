package com.iqbal.inventoryservice.service;

import com.iqbal.inventoryservice.model.InventoryResponse;

public interface InventoryService {

    InventoryResponse isInStock(String skuCode);
}
