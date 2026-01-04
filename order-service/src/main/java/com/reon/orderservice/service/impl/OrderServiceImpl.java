package com.reon.orderservice.service.impl;

import com.reon.orderservice.client.InventoryClient;
import com.reon.orderservice.dto.OrderRequest;
import com.reon.orderservice.model.Order;
import com.reon.orderservice.repository.OrderRepository;
import com.reon.orderservice.response.OrderResponse;
import com.reon.orderservice.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public OrderServiceImpl(OrderRepository orderRepository, InventoryClient inventoryClient) {
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
    }

    @Override
    public OrderResponse newOrder(OrderRequest orderRequest) {
        boolean isProductInStock = inventoryClient.isInStock(orderRequest.getSkuCode(), orderRequest.getQuantity());

        if (isProductInStock) {
            Order order = Order.builder()
                    .skuCode(orderRequest.getSkuCode())
                    .price(orderRequest.getPrice())
                    .quantity(orderRequest.getQuantity())
                    .build();

            String uniqueOrderNumber = UUID.randomUUID().toString();
            order.setOrderNumber(uniqueOrderNumber);

            Order savedOrder =  orderRepository.save(order);

            return OrderResponse.builder()
                    .id(savedOrder.getId())
                    .orderNumber(savedOrder.getOrderNumber())
                    .skuCode(savedOrder.getSkuCode())
                    .price(savedOrder.getPrice())
                    .quantity(savedOrder.getQuantity())
                    .orderTime(savedOrder.getOrderTime())
                    .build();
        } else {
            throw new RuntimeException("Product with sku code " + orderRequest.getSkuCode() + " is not in stock");
        }
    }

    @Override
    public Page<OrderResponse> fetchAllOrders(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(
                order -> OrderResponse.builder()
                        .id(order.getId())
                        .orderNumber(order.getOrderNumber())
                        .skuCode(order.getSkuCode())
                        .price(order.getPrice())
                        .quantity(order.getQuantity())
                        .orderTime(order.getOrderTime())
                        .build()
        );
    }
}
