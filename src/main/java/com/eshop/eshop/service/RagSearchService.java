package com.eshop.eshop.service;

import com.eshop.eshop.dto.ProductResponse;

import java.util.List;

public interface RagSearchService {

    List<ProductResponse> searchSimilarProducts(String query);
}
