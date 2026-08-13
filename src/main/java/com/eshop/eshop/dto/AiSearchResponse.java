package com.eshop.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AiSearchResponse {

    private String message;
    private List<ProductResponse> products;
}

