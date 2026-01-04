package com.reon.inventoryservice.service.impl;

import com.reon.inventoryservice.dto.InventoryRequest;
import com.reon.inventoryservice.model.Inventory;
import com.reon.inventoryservice.repository.InventoryRepository;
import com.reon.inventoryservice.response.InventoryResponse;
import com.reon.inventoryservice.service.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public InventoryResponse newInventory(InventoryRequest inventoryRequest) {

        if (inventoryRepository.existsBySkuCode(inventoryRequest.getSkuCode())) {
            throw new IllegalArgumentException("Sku code already exists.");
        }

        Inventory inventory = Inventory.builder()
                .skuCode(inventoryRequest.getSkuCode())
                .quantity(inventoryRequest.getQuantity())
                .build();
        Inventory savedInventory =  inventoryRepository.save(inventory);

        return InventoryResponse.builder()
                .id(savedInventory.getId())
                .skuCode(savedInventory.getSkuCode())
                .quantity(savedInventory.getQuantity())
                .build();
    }

    @Override
    public Page<InventoryResponse> fetchAllInventories(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Inventory> inventories = inventoryRepository.findAll(pageable);
        return inventories.map(
                inventory -> InventoryResponse.builder()
                        .id(inventory.getId())
                        .skuCode(inventory.getSkuCode())
                        .quantity(inventory.getQuantity())
                        .build()
        );
    }

    @Override
    public boolean isInStock(String skuCode, Integer quantity) {
        return inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode, quantity);
    }
}
