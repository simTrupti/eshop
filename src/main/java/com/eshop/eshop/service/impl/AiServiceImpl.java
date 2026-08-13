package com.eshop.eshop.service.impl;

import com.eshop.eshop.client.ProductClient;
import com.eshop.eshop.dto.AiSearchResponse;
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
    public AiSearchResponse processSearch(String query) {


        // ---------------------------------------------------------
        // 1. Tell Gemini what the user wants
        // ---------------------------------------------------------

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

        // ---------------------------------------------------------
        // 2. Define the tool available to Gemini
        // ---------------------------------------------------------

//        Map<String, Object> schema = Map.of(
//                "type", "object",
//                "properties", Map.of(
//                        "category", Map.of(
//                                "type", "string",
//                                "description", "The product category the user wants"
//                        ),
//                        "maxPrice", Map.of(
//                                "type", "number",
//                                "description", "The maximum price the user is willing to pay"
//                        )
//                ),
//                "required", List.of("category", "maxPrice")
//        );

        Map<String, Object> searchProductsTool = Map.of(
                "type", "function",
                "name", "searchProducts",
                "description", "Search products by category and maximum price",
                "parameters", Map.of(
                        "type", "object",
                        "properties", Map.of(
                                "category", Map.of(
                                        "type", "string",
                                        "description", "Product category to search for"
                                ),
                                "maxPrice", Map.of(
                                        "type", "number",
                                        "description", "Maximum product price"
                                )
                        ),
                        "required", List.of("category", "maxPrice")
                )
        );


        // ---------------------------------------------------------
        // 3. First request to Gemini
        // ---------------------------------------------------------


        Map<String, Object> requestBody = Map.of(
                "model", "gemini-3.6-flash",
                "input", prompt,
//                "response_format", Map.of(
//                        "type", "text",
//                        "mime_type", "application/json",
//                        "schema", schema
//                )
                "tools", List.of(searchProductsTool)
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

            // ---------------------------------------------------------
            // 4. Read Gemini response
            // ---------------------------------------------------------

            var root = objectMapper.readTree(response);

            String interactionId = root.path("id").asText();

            var toolCall = root.path("steps").get(1);

            String toolCallId = toolCall.path("id").asText();

            String toolName = toolCall.path("name").asText();

            double maxPrice = toolCall
                    .path("arguments")
                    .path("maxPrice")
                    .asDouble();

            String category = toolCall
                    .path("arguments")
                    .path("category")
                    .asText();

            System.out.println("TOOL: " + toolName);
            System.out.println("CATEGORY: " + category);
            System.out.println("MAX PRICE: " + maxPrice);

            // ---------------------------------------------------------
            // 5. Execute the requested tool
            // ---------------------------------------------------------

            if ("searchProducts".equals(toolName)) {

                List<ProductResponse> products =
                        productClient.searchProducts(
                                category,
                                maxPrice
                        );

                // ---------------------------------------------------------
                // 6. Convert tool result to JSON
                // ---------------------------------------------------------

                String toolResult =
                        objectMapper.writeValueAsString(products);

                // ---------------------------------------------------------
                // 7. Create function result
                // ---------------------------------------------------------

Map<String, Object> toolResultInput = Map.of(
        "type", "function_result",
        "name", toolName,
        "call_id", toolCallId,
        "result", List.of(
                Map.of(
                        "type", "text",
                        "text", toolResult
                )
        )
);

                // ---------------------------------------------------------
                // 8. Continue the same Gemini interaction
                // ---------------------------------------------------------

                Map<String, Object> continuationBody = Map.of(
                        "model", "gemini-3.6-flash",
                        "previous_interaction_id", interactionId,
                        "input", List.of(toolResultInput),
                        "tools", List.of(searchProductsTool)
                );

String finalResponse = webclientBuilder.build()
        .post()
        .uri("https://generativelanguage.googleapis.com/v1beta/interactions")
        .header("x-goog-api-key", apiKey)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(continuationBody)
        .retrieve()
        .onStatus(
                status -> status.isError(),
                clientResponse -> clientResponse.bodyToMono(String.class)
                        .map(errorBody -> new RuntimeException(
                                "Gemini continuation error: " + errorBody
                        ))
        )
        .bodyToMono(String.class)
        .block();

                return new AiSearchResponse(
                        finalResponse,
                        products
                );
            }

            throw new IllegalArgumentException(
                    "Unsupported tool: " + toolName
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to process AI response",
                    e
            );
        }
    }
}

