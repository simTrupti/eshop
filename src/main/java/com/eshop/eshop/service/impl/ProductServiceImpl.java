package com.eshop.eshop.service.impl;

import com.eshop.eshop.model.entity.Product;
import com.eshop.eshop.model.entity.User;
import com.eshop.eshop.repository.ProductRepository;
import com.eshop.eshop.service.ProductService;
//import com.eshop.eshop.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

   // List<Product> products = new ArrayList<>();
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(Product product){
       // product.setId(IdGenerator.generateId());
       // products.add(product);
        //return product;
        return productRepository.save(product);

    }

@Override
    public List<Product> getAllProducts(){

        //return  products;
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(int id){
//        for(Product product: products) {
//            if (product.getId() == id) {
//                return product;
//            }
//
//        }
//        return null;
        Optional<Product> opt = productRepository.findById(id);
        return opt.orElse(null);
    }
}
