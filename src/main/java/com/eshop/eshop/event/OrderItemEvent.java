package com.eshop.eshop.event;

public class OrderItemEvent {


    private Integer productId;
    private Integer quantity;

    public OrderItemEvent() {}

    public OrderItemEvent(Integer productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
