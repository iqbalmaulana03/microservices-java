package com.iqbal.orderservice.event;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent {
    private String orderNumber;
}
