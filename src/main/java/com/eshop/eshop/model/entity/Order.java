package com.eshop.eshop.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String orderDate;
    private double totalAmount;
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    // Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;}

    public String getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(String orderDate) {
        if(orderDate != null && !orderDate.isEmpty()) {
            this.orderDate = orderDate;
        } else {
            System.out.println("Order date is required");
        }
    }

    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        if(status != null && !status.isEmpty()) {
            this.status = status;
        } else {
            System.out.println("Status is required");
        }
    }
}

