package com.order.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for {@link com.order.orderservice.model.OrderLineItems}
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItemsDto {
    Long id;
    String squId;
    BigDecimal price;
    Integer quantity;
}