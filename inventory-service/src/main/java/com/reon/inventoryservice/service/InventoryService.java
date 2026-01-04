package com.reon.inventoryservice.service;

import com.reon.inventoryservice.dto.InventoryRequest;
import com.reon.inventoryservice.response.InventoryResponse;
import org.springframework.data.domain.Page;

public interface InventoryService {
    InventoryResponse newInventory(InventoryRequest inventoryRequest);
    Page<InventoryResponse> fetchAllInventories(int pageNo, int pageSize);
}
