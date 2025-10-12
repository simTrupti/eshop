package com.eshop.eshop.service;

import com.eshop.eshop.model.entity.OrderItem;

import java.util.List;

public interface OrderItemService {

    OrderItem createOrderItem(OrderItem orderItem);
    List<OrderItem> getAllOrderItems();
    OrderItem getOrderItemById(int id);
}
