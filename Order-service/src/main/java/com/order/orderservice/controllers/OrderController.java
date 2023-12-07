package com.order.orderservice.controllers;


import com.order.orderservice.dto.InventoryResponse;
import com.order.orderservice.dto.OrderLineItemsDto;
import com.order.orderservice.dto.OrderRequest;
import com.order.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final WebClient.Builder webClient;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {

        log.info("------------Inside the Place order in order service-------------");
        List<String> squCodes = orderRequest.getOrderLineItemsList()
                .stream()
                .map(OrderLineItemsDto::getSquId)
                .toList();
        InventoryResponse[] inventoryResponses = webClient.build().get().
                uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("sku_code", squCodes)
                                .build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class).block();
        assert inventoryResponses != null;
        boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::getIsSquCode);
        if (allProductsInStock) {
            orderService.saveOrder(orderRequest);
            return "Order Placed Successfully";
        }
        return "Items not in inventory";
    }

    @GetMapping("/get")
    public String getStatus() {
        return "This is working";
    }
}
