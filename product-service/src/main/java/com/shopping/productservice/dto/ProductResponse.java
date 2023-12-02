package com.shopping.productservice.dto;

import lombok.*;

import java.math.BigDecimal;


@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
@Data
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
