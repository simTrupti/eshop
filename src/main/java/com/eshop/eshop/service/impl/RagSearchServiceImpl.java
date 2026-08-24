package com.eshop.eshop.service.impl;

import com.eshop.eshop.dto.ProductResponse;
import com.eshop.eshop.service.EmbeddingService;
import com.eshop.eshop.service.RagSearchService;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Points;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static io.qdrant.client.QueryFactory.nearest;
import static io.qdrant.client.WithPayloadSelectorFactory.enable;

@Service
@RequiredArgsConstructor
public class RagSearchServiceImpl implements RagSearchService {

    private final EmbeddingService embeddingService;
    private final QdrantClient qdrantClient;


    @Override
    public List<ProductResponse> searchSimilarProducts(String query) {

        try {

            // 1. Convert user's question into a vector
            float[] embedding =
                    embeddingService.generateEmbedding(query);

            List<Float> vector = new ArrayList<>();

            for (float value : embedding) {
                vector.add(value);
            }

            // 2. Search Qdrant for the most similar products
            List<Points.ScoredPoint> results =
                    qdrantClient.queryAsync(
                            Points.QueryPoints.newBuilder()
                                    .setCollectionName("products")
                                    .setQuery(nearest(vector))
                                    .setLimit(3)
                                    .setWithPayload(enable(true))
                                    .build()
                    ).get();

            // 3. Convert Qdrant payloads into ProductResponse objects
            List<ProductResponse> products = new ArrayList<>();

            for (Points.ScoredPoint result : results) {

                ProductResponse product =
                        new ProductResponse();

                product.setId((int)
                        result.getId().getNum()
                );

                product.setName(
                        result.getPayloadOrThrow("name")
                                .getStringValue()
                );

                product.setDescription(
                        result.getPayloadOrThrow("description")
                                .getStringValue()
                );

                product.setCategory(
                        result.getPayloadOrThrow("category")
                                .getStringValue()
                );

                product.setPrice(
                        result.getPayloadOrThrow("price")
                                .getDoubleValue()
                );

                products.add(product);
            }

            return products;

        } catch (Exception e) {

        e.printStackTrace();

        throw new RuntimeException(
                "Failed to search similar products: " + e.getMessage(), e
        );
    }
    }
}