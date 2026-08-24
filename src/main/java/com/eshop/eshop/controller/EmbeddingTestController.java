package com.eshop.eshop.controller;

import com.eshop.eshop.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class EmbeddingTestController {

    private final EmbeddingService embeddingService;

    @GetMapping("/embedding")
    public String testEmbedding(
            @RequestParam(name = "text") String text) {

        float[] embedding =
                embeddingService.generateEmbedding(text);

        return "Embedding size: " + embedding.length
                + "\nFirst 5 values: "
                + Arrays.toString(
                Arrays.copyOf(embedding, 5)
        );
    }
}