package com.iqbal.inventoryservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WebResponse <T>{

    private String status;

    private String message;

    private T data;
}
