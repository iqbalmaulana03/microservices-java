package com.iqbal.inventoryservice.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InventoryRequest {

    @NotEmpty(message = "Product Id has not be empty!")
    private String productId;

    @NotEmpty(message = "Sku Code has not be empty!")
    private String skuCode;

    @Size(min = 1, message = "Quantity has be minimal 1!")
    private Integer quantity;
}
