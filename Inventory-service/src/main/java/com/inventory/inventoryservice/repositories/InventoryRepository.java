package com.inventory.inventoryservice.repositories;

import com.inventory.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean findBySkuCode(String squCode);
}
