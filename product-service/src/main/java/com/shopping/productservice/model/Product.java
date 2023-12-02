package com.shopping.productservice.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Document(value="product")
public class Product {

    @Id
    private String id;
    private String name;
    private String quantity;
    private String description;
    private BigDecimal price;
}
