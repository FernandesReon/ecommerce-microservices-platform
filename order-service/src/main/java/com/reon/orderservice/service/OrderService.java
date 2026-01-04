package com.reon.orderservice.service;

import com.reon.orderservice.dto.OrderRequest;
import com.reon.orderservice.response.OrderResponse;
import org.springframework.data.domain.Page;

public interface OrderService {
    OrderResponse newOrder(OrderRequest orderRequest);
    Page<OrderResponse> fetchAllOrders(int pageNo, int pageSize);
}
