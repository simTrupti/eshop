package com.eshop.eshop.service.impl;

import com.eshop.eshop.client.ProductClient;
import com.eshop.eshop.dto.ProductResponse;
import com.eshop.eshop.dto.ShoppingIntent;
import com.eshop.eshop.service.AiService;
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
public class AiServiceImpl implements AiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper;

    private final WebClient.Builder webclientBuilder;

    private final ProductClient productClient;

    @Override
    public List<ProductResponse> processSearch(String query) {

        String prompt = """
        Extract shopping criteria from the user's request.

        Choose the category ONLY from this list:
        Electronics
        Footwear
        Clothing
        Accessories
        Home
        Fitness
        Furniture

        Return the category and maximum price.

        User request:
        %s
        """.formatted(query);

        Map<String, Object> schema = Map.of(
                "type", "object",
                "properties", Map.of(
                        "category", Map.of(
                                "type", "string",
                                "description", "The product category the user wants"
                        ),
                        "maxPrice", Map.of(
                                "type", "number",
                                "description", "The maximum price the user is willing to pay"
                        )
                ),
                "required", List.of("category", "maxPrice")
        );

        Map<String, Object> requestBody = Map.of(
                "model", "gemini-3.6-flash",
                "input", prompt,
                "response_format", Map.of(
                        "type", "text",
                        "mime_type", "application/json",
                        "schema", schema
                )
        );

        try {

            String response = webclientBuilder.build()
                    .post()
                    .uri("https://generativelanguage.googleapis.com/v1beta/interactions")
                    .header("x-goog-api-key", apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        System.out.println("GEMINI RESPONSE:");
        System.out.println(response);

            String json = objectMapper.readTree(response)
                    .path("steps")
                    .get(1)
                    .path("content")
                    .get(0)
                    .path("text")
                    .asText();

            ShoppingIntent intent =
                    objectMapper.readValue(json, ShoppingIntent.class);

            return productClient.searchProducts(
                    intent.getCategory(),
                    intent.getMaxPrice()
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to process AI response", e
            );
        }
    }
}