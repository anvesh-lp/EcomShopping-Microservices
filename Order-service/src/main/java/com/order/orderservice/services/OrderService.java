package com.order.orderservice.services;


import com.order.orderservice.dto.OrderLineItemsDto;
import com.order.orderservice.dto.OrderRequest;
import com.order.orderservice.model.Order;
import com.order.orderservice.model.OrderLineItems;
import com.order.orderservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private OrderLineItems convertToOrderline(OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItems.builder()
                .id(orderLineItemsDto.getId())
                .skuCode(orderLineItemsDto.getSquId())
                .price(orderLineItemsDto.getPrice())
                .quantity(orderLineItemsDto.getQuantity())
                .build();
    }

    public void saveOrder(OrderRequest orderRequest) {
        List<OrderLineItems> orders = orderRequest.getOrderLineItemsList()
                .stream()
                .map(this::convertToOrderline)
                .toList();
        Order order = new Order();
        order.setOrderLineItemsList(orders);
        orderRepository.save(order);
        log.info("Order with {} ID placed successfully",order.getId());
    }
}
