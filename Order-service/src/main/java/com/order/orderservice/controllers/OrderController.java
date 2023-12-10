package com.order.orderservice.controllers;


import com.order.orderservice.dto.InventoryResponse;
import com.order.orderservice.dto.OrderEvent;
import com.order.orderservice.dto.OrderLineItemsDto;
import com.order.orderservice.dto.OrderRequest;
import com.order.orderservice.services.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final WebClient.Builder webClient;
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "services", fallbackMethod = "onFallBack")
    @TimeLimiter(name = "services")
    public CompletionStage<String> placeOrder(@RequestBody OrderRequest orderRequest) {

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
            Long id = orderService.saveOrder(orderRequest);
            kafkaTemplate.send("notificationTopic", OrderEvent.builder().id("start").orderId("" + id).build());
            return CompletableFuture.completedFuture("Order Placed Successfully");

        }
        return CompletableFuture.completedFuture("Order not placed");
    }

    @GetMapping("/get")
    public String getStatus() {
        return "This is working";
    }

    public CompletionStage<String> onFallBack(OrderRequest orderRequest, Exception e) {
        return CompletableFuture.completedFuture("oops! something unexpected happened!! please try again " + e.getMessage());
    }
}
