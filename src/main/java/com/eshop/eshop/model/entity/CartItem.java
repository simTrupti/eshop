package com.eshop.eshop.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(nullable = false)
    private int quantity;

    private Integer productId;
}
