package com.eshop.eshop.client;

import com.eshop.eshop.dto.ProductResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class ProductClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String PRODUCT_SERVICE_URL =
            "http://localhost:9090/api/products/internal";

    public ProductResponse reduceStock(int productId, int quantity) {

        String url = PRODUCT_SERVICE_URL + "/" + productId + "/stock";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Integer> request = new HttpEntity<>(quantity, headers);

        ResponseEntity<ProductResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.PUT,
                        request,
                        ProductResponse.class
                );

        return response.getBody();
    }

    public List<ProductResponse> searchProducts(String category, Double maxPrice) {

        String url = "http://localhost:9090/api/products/search"
                + "?category=" + category
                + "&maxPrice=" + maxPrice;

        ResponseEntity<ProductResponse[]> response =
                restTemplate.getForEntity(
                        url,
                        ProductResponse[].class
                );

        return Arrays.asList(response.getBody());
    }
}
