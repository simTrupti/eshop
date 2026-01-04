package com.eshop.eshop.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaceOrderRequest {

    @NotNull
    private Integer userId;

    @NotEmpty
    private List<OrderItemRequest> items;
}
