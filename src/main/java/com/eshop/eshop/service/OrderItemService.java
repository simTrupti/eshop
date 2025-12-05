package com.eshop.eshop.service;

import com.eshop.eshop.model.entity.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {

    OrderItem createOrderItem(OrderItem orderItem);

    List<OrderItem> getAllOrderItems();

    Optional<OrderItem> getOrderItemById(int id);
}
