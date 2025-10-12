package com.eshop.eshop.service;

import com.eshop.eshop.model.entity.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(Order order);
    List<Order> getAllOrders();
    Order getOrderById(int id);
}
