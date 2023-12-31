package com.inventory.inventoryservice;

import com.inventory.inventoryservice.model.Inventory;
import com.inventory.inventoryservice.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
//@EnableEurekaClient
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
        log.info("Loading Data into Database..............");
        return args -> {
            Inventory inventory1 = Inventory.builder().skuCode("Iphone_13").quantity(100).build();
            Inventory inventory2 = Inventory.builder().skuCode("samsung").quantity(0).build();
            inventoryRepository.save(inventory1);
            inventoryRepository.save(inventory2);
        };
    }
}
