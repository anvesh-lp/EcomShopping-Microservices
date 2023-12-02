package com.shopping.productservice.dto;

import lombok.*;

import java.math.BigDecimal;


@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
}
