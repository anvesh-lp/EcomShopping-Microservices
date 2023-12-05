package com.order.orderservice.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InventoryResponse {
    private String squCode;
    private Boolean isSquCode;
}
