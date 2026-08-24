package com.eshop.eshop.service.impl;

import com.eshop.eshop.service.EmbeddingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmbeddingServiceImpl implements EmbeddingService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper;
    private final WebClient.Builder webClientBuilder;

    @Override
    public float[] generateEmbedding(String text) {

        Map<String, Object> requestBody = Map.of(
                "content", Map.of(
                        "parts", List.of(
                                Map.of("text", text)
                        )
                ),
                "outputDimensionality", 768
        );

        try {

            String response = webClientBuilder.build()
                    .post()
                    .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-embedding-001:embedContent")
                    .header("x-goog-api-key", apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return objectMapper.readTree(response)
                    .path("embedding")
                    .path("values")
                    .traverse(objectMapper)
                    .readValueAs(float[].class);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to generate embedding", e
            );
        }
    }
}