package com.eshop.eshop.service;

import com.eshop.eshop.model.entity.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(int id);


}
