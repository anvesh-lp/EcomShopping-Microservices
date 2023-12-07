package com.inventory.inventoryservice.controllers;


import com.inventory.inventoryservice.dto.InventoryResponse;
import com.inventory.inventoryservice.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam("sku_code") List<String> skuCodes) {
        return inventoryService.isSkuCode(skuCodes);
    }

}
