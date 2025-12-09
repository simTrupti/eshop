package com.eshop.eshop.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Order date is required")
    private String orderDate;

    @Positive(message = "Total amount must be positive")
    private double totalAmount;

    @NotBlank(message = "Status is required")
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "User is required")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

//    // Getters and setters
//    public int getId() {
//        return id;
//    }
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public User getUser() {
//        return user;
//    }
//    public void setUser(User user) {
//        this.user = user;}
//
//    public String getOrderDate() {
//        return orderDate;
//    }
//    public void setOrderDate(String orderDate) {
//            this.orderDate = orderDate;
//    }
//
//    public double getTotalAmount() {
//        return totalAmount;
//    }
//    public void setTotalAmount(double totalAmount) {
//        this.totalAmount = totalAmount;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//    public void setStatus(String status) {
//            this.status = status;
//    }
}

