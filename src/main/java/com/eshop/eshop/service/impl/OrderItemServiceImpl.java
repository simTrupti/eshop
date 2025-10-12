package com.eshop.eshop.service.impl;

import com.eshop.eshop.model.entity.OrderItem;
import com.eshop.eshop.repository.OrderItemRepository;
import com.eshop.eshop.service.OrderItemService;
//import com.eshop.eshop.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    //private List<OrderItem> orderItems = new ArrayList<>();
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
       // orderItem.setId(IdGenerator.generateId());
       // orderItems.add(orderItem);
        //return orderItem;
        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        //return orderItems;
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem getOrderItemById(int id) {
//        for (OrderItem item : orderItems) {
//            if (item.getId() == id) {
//                return item;
//            }
//        }
//        return null;
        return orderItemRepository.findById(id).orElse(null);
    }

}
