package com.eshop.eshop.event;

import java.util.List;

public class OrderPlacedEvent {


    private Integer orderId;
    private Integer userId;
    private Double totalAmount;
    private List<OrderItemEvent> items;

    public OrderPlacedEvent() {}

    public OrderPlacedEvent(
            Integer orderId,
            Integer userId,
            Double totalAmount,
            List<OrderItemEvent> items
    ) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Integer getUserId() {
        return userId;
    }
    public Double getTotalAmount() {
        return totalAmount;
    }

    public List<OrderItemEvent> getItems() {
        return items;
    }

}
