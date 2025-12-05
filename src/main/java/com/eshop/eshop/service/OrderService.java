package com.eshop.eshop.service;

import com.eshop.eshop.model.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order createOrder(Order order);
    List<Order> getAllOrders();

    // ✅ Java 8 Optional style
    Optional<Order> getOrderById(int id);
}
