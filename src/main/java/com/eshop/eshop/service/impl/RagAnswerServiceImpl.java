package com.eshop.eshop.service.impl;

import com.eshop.eshop.dto.ProductResponse;
import com.eshop.eshop.service.RagAnswerService;
import com.eshop.eshop.service.RagSearchService;
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
public class RagAnswerServiceImpl implements RagAnswerService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RagSearchService ragSearchService;
    private final ObjectMapper objectMapper;
    private final WebClient.Builder webClientBuilder;

    @Override
    public String generateAnswer(String query) {

        try {

            // 1. Retrieve relevant products from Qdrant
            List<ProductResponse> products =
                    ragSearchService.searchSimilarProducts(query);

            // 2. Convert retrieved products into JSON
            String productContext =
                    objectMapper.writeValueAsString(products);

            // 3. Give the retrieved context to Gemini
            String prompt = """
                    You are an e-commerce shopping assistant.

                    Answer the user's question using ONLY the
                    products provided below.

                    If the products do not contain enough information
                    to answer the question, say so.

                    User question:
                    %s

                    Retrieved products:
                    %s
                    """.formatted(query, productContext);

            Map<String, Object> requestBody = Map.of(
                    "model", "gemini-3.6-flash",
                    "input", prompt
            );

            // 4. Call Gemini
            String response = webClientBuilder.build()
                    .post()
                    .uri("https://generativelanguage.googleapis.com/v1beta/interactions")
                    .header("x-goog-api-key", apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // 5. Extract Gemini's text response
            var root = objectMapper.readTree(response);

            return root.path("steps")
                    .get(root.path("steps").size() - 1)
                    .path("content")
                    .get(0)
                    .path("text")
                    .asText();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to generate RAG answer", e
            );
        }
    }
}