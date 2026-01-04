package com.reon.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "inventory", url = "${inventory.service.url}")
public interface InventoryClient {

    @GetMapping("/api/v1/inventory")
    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);
}
