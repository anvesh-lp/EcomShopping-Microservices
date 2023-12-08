package com.inventory.inventoryservice.services;


import com.inventory.inventoryservice.dto.InventoryResponse;
import com.inventory.inventoryservice.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public List<InventoryResponse> isSkuCode(List<String> skuCode) {
        return inventoryRepository.findBySkuCodeIn(skuCode).stream().map(inventory -> {
            return InventoryResponse
                    .builder()
                    .squCode(inventory.getSkuCode())
                    .isSquCode(inventory.getQuantity() > 1)
                    .build();
        }).toList();
    }
}
