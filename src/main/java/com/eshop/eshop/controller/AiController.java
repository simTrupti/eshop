package com.eshop.eshop.controller;

import com.eshop.eshop.dto.AiSearchRequest;
import com.eshop.eshop.dto.AiSearchResponse;
import com.eshop.eshop.dto.ProductResponse;
import com.eshop.eshop.dto.ShoppingIntent;
import com.eshop.eshop.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/search")
    public AiSearchResponse search(@RequestBody AiSearchRequest  request){
        return aiService.processSearch(request.getQuery());
    }
}
