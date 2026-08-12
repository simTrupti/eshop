package com.eshop.eshop.service;

import com.eshop.eshop.dto.ProductResponse;
import com.eshop.eshop.dto.ShoppingIntent;

import java.util.List;

public interface AiService {

    List<ProductResponse> processSearch(String query);
}
