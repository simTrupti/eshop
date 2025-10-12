package com.eshop.eshop.controller;


import com.eshop.eshop.model.entity.OrderItem;
import com.eshop.eshop.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderitems")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping
    public OrderItem createOrderItem(@RequestBody OrderItem orderItem) {
        return orderItemService.createOrderItem(orderItem);
    }

    @GetMapping
    public List<OrderItem> getAllOrderItems() {
        return orderItemService.getAllOrderItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable int id) {
        OrderItem orderItem = orderItemService.getOrderItemById(id);
        if (orderItem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderItem);
    }
}
