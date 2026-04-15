package com.eshop.eshop.service;

import com.eshop.eshop.dto.OrderItemRequest;
import com.eshop.eshop.dto.PlaceOrderRequest;
import com.eshop.eshop.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    //Order createOrder(Order order);

    Page<Order> getAllOrders(Pageable pageable);

    Optional<Order> getOrderById(int id);

    Order placeOrder(PlaceOrderRequest request);
}
