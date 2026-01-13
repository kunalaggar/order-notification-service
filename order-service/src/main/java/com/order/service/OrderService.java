package com.order.service;

import com.order.entity.Order;
import com.order.model.OrderRequest;

public interface OrderService {

    Order createOrder(OrderRequest request);

    Order getOrderById(Long id);

    Iterable<Order> getAllOrders();
}

