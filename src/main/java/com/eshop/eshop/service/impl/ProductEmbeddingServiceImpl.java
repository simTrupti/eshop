package com.eshop.eshop.service.impl;

import com.eshop.eshop.client.ProductClient;
import com.eshop.eshop.dto.ProductResponse;
import com.eshop.eshop.service.EmbeddingService;
import com.eshop.eshop.service.ProductEmbeddingService;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.JsonWithInt;
import io.qdrant.client.grpc.Points;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductEmbeddingServiceImpl
        implements ProductEmbeddingService {

    private final ProductClient productClient;
    private final EmbeddingService embeddingService;
    private final QdrantClient qdrantClient;

    @Override
    public void indexProducts() {

        try {

            List<ProductResponse> products =
                    productClient.getAllProducts();

            for (ProductResponse product : products) {

                String text = product.getName()
                        + ". "
                        + product.getDescription()
                        + ". Category: "
                        + product.getCategory();

                float[] embedding =
                        embeddingService.generateEmbedding(text);

                List<Float> vector = new ArrayList<>();

                for (float value : embedding) {
                    vector.add(value);
                }

                Points.PointStruct point =
                        Points.PointStruct.newBuilder()
                                .setId(
                                        Points.PointId.newBuilder()
                                                .setNum(product.getId())
                                                .build()
                                )
                                .setVectors(
                                        Points.Vectors.newBuilder()
                                                .setVector(
                                                        Points.Vector.newBuilder()
                                                                .addAllData(vector)
                                                                .build()
                                                )
                                                .build()
                                )
                                .putPayload(
                                        "name",
                                        JsonWithInt.Value.newBuilder()
                                                .setStringValue(product.getName())
                                                .build()
                                )
                                .putPayload(
                                        "description",
                                        JsonWithInt.Value.newBuilder()
                                                .setStringValue(product.getDescription())
                                                .build()
                                )
                                .putPayload(
                                        "category",
                                        JsonWithInt.Value.newBuilder()
                                                .setStringValue(product.getCategory())
                                                .build()
                                )
                                .putPayload(
                                        "price",
                                        JsonWithInt.Value.newBuilder()
                                                .setDoubleValue(product.getPrice())
                                                .build()
                                )
                                .build();

                qdrantClient.upsertAsync(
                        "products",
                        List.of(point)
                ).get();

                System.out.println(
                        "Indexed product: " + product.getName()
                );
            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new RuntimeException(
                    "Product indexing was interrupted", e
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to index products", e
            );
        }
    }
}