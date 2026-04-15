package com.eshop.eshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequest {

    private Integer userId;
    private Integer productId;
    private int quantity;
}
