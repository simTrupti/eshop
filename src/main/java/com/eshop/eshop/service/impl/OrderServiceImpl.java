package com.eshop.eshop.service.impl;


import com.eshop.eshop.client.ProductClient;
import com.eshop.eshop.dto.OrderItemRequest;
import com.eshop.eshop.dto.PlaceOrderRequest;
import com.eshop.eshop.dto.ProductResponse;
import com.eshop.eshop.event.OrderItemEvent;
import com.eshop.eshop.event.OrderPlacedEvent;
import com.eshop.eshop.kafka.OrderProducer;
import com.eshop.eshop.model.entity.Order;
import com.eshop.eshop.model.entity.OrderItem;
import com.eshop.eshop.model.entity.Product;
import com.eshop.eshop.model.entity.User;
import com.eshop.eshop.repository.OrderRepository;
import com.eshop.eshop.service.OrderService;
//import com.eshop.eshop.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    //private List<Order> orders = new ArrayList<>();
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private OrderProducer orderProducer;

    // ✅ Create Order
//    @Override
//    public Order createOrder(Order order) {
//        //order.setId(IdGenerator.generateId());
//        //orders.add(order);
//       // return order;
//        return orderRepository.save(order);
//    }

    @Override
    public List<Order> getAllOrders() {
       // return orders;
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order placeOrder(PlaceOrderRequest request) {

        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        if( request.getItems() == null|| request.getItems().isEmpty()){
            throw new IllegalArgumentException("Order must contain atleast one item");
        }

    request.getItems().forEach(item -> {
        if (item.getProductId() == null) {
            throw new IllegalArgumentException("Product ID is required");
        }
        if (item.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
    });

    //  Validate product, reduce stock, calculate price
    List<OrderItem> orderItems = new ArrayList<>();

    double totalAmount = request.getItems().stream()
            .mapToDouble(item -> {

            ProductResponse product;
            try {
                product = productClient.reduceStock(
                        item.getProductId(),
                        item.getQuantity()
                );
            } catch (Exception ex) {
                throw new IllegalArgumentException(
                        "Product not found or stock insufficient: " + item.getProductId()
                );
            }

                OrderItem orderItem = new OrderItem();
                orderItem.setProductId(product.getId());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setPrice(product.getPrice()); // price snapshot

                orderItems.add(orderItem);

                return product.getPrice() * item.getQuantity();
            })
            .sum();

    // STEP 5: Create Order
    Order order = new Order();
        User user = new User();
        user.setId(request.getUserId());
        order.setUser(user);
    order.setStatus("PLACED");
    order.setTotalAmount(totalAmount);
    order.setOrderDate(LocalDate.now().toString());

    // STEP 6: Link OrderItems → Order
    orderItems.forEach(orderItem -> orderItem.setOrder(order));
    order.setOrderItems(orderItems);

    Order savedOrder = orderRepository.save(order);

        List<OrderItemEvent> eventItems = orderItems.stream()
                .map(item -> new OrderItemEvent(
                        item.getProductId(),
                        item.getQuantity()
                ))
                .toList();

        OrderPlacedEvent event = new OrderPlacedEvent(
                savedOrder.getId(),
                request.getUserId(),
                savedOrder.getTotalAmount(),
                eventItems
        );

        orderProducer.sendOrderPlacedEvent(event);

        return savedOrder;
}


}


