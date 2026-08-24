package com.eshop.eshop.config;

import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QdrantConfig {

    @Bean
    public QdrantClient qdrantClient() {

        return new QdrantClient(
                QdrantGrpcClient.newBuilder(
                        "localhost",
                        6334,
                        false
                ).build()
        );
    }

    public String createProductCollection(QdrantClient qdrantClient) {

        try {
            qdrantClient.createCollectionAsync(
                    "products",
                    io.qdrant.client.grpc.Collections.VectorParams.newBuilder()
                            .setSize(768)
                            .setDistance(
                                    io.qdrant.client.grpc.Collections.Distance.Cosine
                            )
                            .build()
            ).get();

        } catch (Exception e) {
            System.out.println("Collection may already exist: " + e.getMessage());
        }

        return "products";
    }
}
