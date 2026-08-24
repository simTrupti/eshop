package com.eshop.eshop.controller;

import com.eshop.eshop.dto.ProductResponse;
import com.eshop.eshop.service.ProductEmbeddingService;
import com.eshop.eshop.service.RagAnswerService;
import com.eshop.eshop.service.RagSearchService;
import com.eshop.eshop.service.RagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rag")
@RequiredArgsConstructor
public class RagController {

    private final ProductEmbeddingService productEmbeddingService;
    private final RagService ragService;
    private final RagSearchService ragSearchService;
    private final RagAnswerService ragAnswerService;

    @PostMapping("/index")
    public String indexProducts() {

        productEmbeddingService.indexProducts();

        return "Products indexed successfully";
    }

    @PostMapping("/create-collection")
    public String createCollection() {

        ragService.createCollection();

        return "Qdrant collection created";
    }

    @GetMapping("/search")
    public List<ProductResponse> search(
            @RequestParam String query) {

        return ragSearchService.searchSimilarProducts(query);
    }

    @GetMapping("/answer")
    public String answer(
            @RequestParam String query) {

        return ragAnswerService.generateAnswer(query);
    }
}
