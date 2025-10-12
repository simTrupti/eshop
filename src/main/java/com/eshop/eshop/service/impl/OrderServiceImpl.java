package com.eshop.eshop.service.impl;


import com.eshop.eshop.model.entity.Order;
import com.eshop.eshop.repository.OrderRepository;
import com.eshop.eshop.service.OrderService;
//import com.eshop.eshop.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    //private List<Order> orders = new ArrayList<>();
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        //order.setId(IdGenerator.generateId());
        //orders.add(order);
       // return order;
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
       // return orders;
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(int id) {
//        for (Order order : orders) {
//            if (order.getId() == id) {
//                return order;
//            }
//        }
//        return null;
        return  orderRepository.findById(id).orElse(null);
    }
}
