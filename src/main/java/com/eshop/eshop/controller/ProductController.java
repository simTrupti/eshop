package com.eshop.eshop.controller;

import com.eshop.eshop.model.entity.Product;
import com.eshop.eshop.model.entity.User;
import com.eshop.eshop.service.ProductService;
import com.eshop.eshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController{
    @Autowired
    private ProductService productService;

    @PostMapping
    public Product createProduct(@RequestBody Product product){
        Product createNewProduct = productService.createProduct(product);
        return createNewProduct;
    }

    @GetMapping
    public List<Product> getAllProducts(){

        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
        Product product = productService.getProductById(id);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);

    }



}