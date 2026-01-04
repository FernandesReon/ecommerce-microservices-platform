package com.reon.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    @NotBlank(message = "Sku code is required.")
    private String skuCode;
    @NotNull(message = "Order price cannot be null")
    private BigDecimal price;
    @NotNull(message = "Quantity cannot be null")
    private Integer quantity;
}
