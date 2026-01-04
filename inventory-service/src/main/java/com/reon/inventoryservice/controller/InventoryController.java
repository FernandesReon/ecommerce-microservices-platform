package com.reon.inventoryservice.controller;

import com.reon.inventoryservice.dto.InventoryRequest;
import com.reon.inventoryservice.response.InventoryResponse;
import com.reon.inventoryservice.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/newInventory")
    public ResponseEntity<InventoryResponse> newInventory(@Valid @RequestBody InventoryRequest inventoryRequest) {
        InventoryResponse response = inventoryService.newInventory(inventoryRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<InventoryResponse>> getAllInventory(@RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
                                                                   @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        Page<InventoryResponse> inventories = inventoryService.fetchAllInventories(pageNo, pageSize);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(inventories);
    }
}
