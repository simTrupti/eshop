package com.eshop.eshop.service;

import com.eshop.eshop.model.entity.Product;
import com.eshop.eshop.model.entity.dto.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product);
    List<Product> getAllProducts();
 //   Product getProductById(int id);
    ProductResponse getProductByIdDto(Integer id);
    Page<ProductResponse> listProducts(String q, String category, Double min, Double max,
                                       int page, int size, String sort);


}
