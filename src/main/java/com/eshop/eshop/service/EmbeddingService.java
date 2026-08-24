package com.eshop.eshop.service;

import com.eshop.eshop.dto.ProductResponse;

import java.util.List;

public interface EmbeddingService {

    float[] generateEmbedding(String text);

}
