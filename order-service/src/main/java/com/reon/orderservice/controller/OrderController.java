package com.reon.orderservice.controller;

import com.reon.orderservice.dto.OrderRequest;
import com.reon.orderservice.response.OrderResponse;
import com.reon.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/newOrder")
    public ResponseEntity<OrderResponse> generateNewOrder(@Valid @RequestBody OrderRequest orderRequest) {
        OrderResponse response = orderService.newOrder(orderRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<OrderResponse>> getAllOrders(@RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
                                                            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        Page<OrderResponse> ordersList = orderService.fetchAllOrders(pageNo, pageSize);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ordersList);
    }
}
