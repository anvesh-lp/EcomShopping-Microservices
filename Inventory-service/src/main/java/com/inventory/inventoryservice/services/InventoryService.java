package com.inventory.inventoryservice.services;


import com.inventory.inventoryservice.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean isSkuCode(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode);
    }
}
