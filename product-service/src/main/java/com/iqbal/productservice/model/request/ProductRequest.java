package com.iqbal.productservice.model.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductRequest {

    @NotEmpty(message = "Product Name not has be empty!")
    private String name;

    @NotEmpty(message = "Description not has be empty!")
    private String description;

    @DecimalMin(value = "100.00", message = "Price must be at least 100.00")
    private BigDecimal price;
}
