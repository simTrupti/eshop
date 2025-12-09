package com.eshop.eshop.service.impl;

import com.eshop.eshop.model.entity.Product;
import com.eshop.eshop.model.entity.User;
import com.eshop.eshop.model.entity.dto.ProductResponse;
import com.eshop.eshop.repository.ProductRepository;
import com.eshop.eshop.repository.specs.ProductSpecifications;
import com.eshop.eshop.service.ProductService;
//import com.eshop.eshop.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> createProduct(List<Product> products) {

    // Loop through each product one by one
    products.forEach(p -> {

        // Normalize the product name
        p.setName(p.getName().toLowerCase());

        // Check for duplicate name
        productRepository.findByName(p.getName())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException(
                            "Product already exists: " + p.getName()
                    );
                });
    });

    // Save all products in one go
    return productRepository.saveAll(products);
}



    // ✅ Get All Products
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Map<String, Long> getProductCountByCategory() {

            return  productRepository.findAll().stream()
                    .collect(Collectors.groupingBy(
                            Product::getCategory,
                            Collectors.counting()
                    ));


}
}




