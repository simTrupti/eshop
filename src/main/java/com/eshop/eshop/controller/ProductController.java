package com.eshop.eshop.controller;

import com.eshop.eshop.model.entity.Product;
import com.eshop.eshop.model.entity.User;
import com.eshop.eshop.model.entity.dto.ProductResponse;
import com.eshop.eshop.service.ProductService;
import com.eshop.eshop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // ✅ Create Product with validation
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody List<Product> products) {
        try {
            List<Product> createdProduct = productService.createProduct(products);
            return ResponseEntity.status(201).body(createdProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating product: " + e.getMessage());
        }
    }

    // ✅ Get all products
    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.status(201).body(products);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving products: " + e.getMessage());
        }
    }

    //get Categories count
    @GetMapping("/countcategory")
    public ResponseEntity<Map<String, Long>> getCategoryCount(){
        return ResponseEntity.ok(productService.getProductCountByCategory());
    }



//    // ✅ Get product by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
//        try {
//            Product product = productService.getProductById(id);
//            if (product == null) {
//                return ResponseEntity.notFound().build();
//            }
//            return ResponseEntity.status(201).body(product);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error retrieving product: " + e.getMessage());
//        }
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getProductByIddto(@PathVariable Integer id){
//        try{
//            ProductResponse product = productService.getProductByIdDto(id);
//            return ResponseEntity.ok(product);
//        }catch (RuntimeException e){
//            return ResponseEntity.status(404).body(e.getMessage());
//        }catch (Exception e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//
//    }
//
//    @GetMapping
//    public ResponseEntity<?> listProducts(
//            @RequestParam(value="q", required=false) String q,
//            @RequestParam(value="category", required=false) String category,
//            @RequestParam(value="min", required=false) Double min,
//            @RequestParam(value="max", required=false) Double max,
//            @RequestParam(value="page", defaultValue="0") int page,
//            @RequestParam(value="size", defaultValue="20") int size,
//            @RequestParam(value="sort", defaultValue="id,asc") String sort
//    ) {
//        try {
//            Page<ProductResponse> results = productService.listProducts(q, category, min, max, page, size, sort);
//            return ResponseEntity.ok(results);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }



}
