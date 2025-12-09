package com.eshop.eshop.service;

import com.eshop.eshop.model.entity.Product;
import com.eshop.eshop.model.entity.dto.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ProductService {

    List<Product> createProduct(List<Product> products);

    List<Product> getAllProducts();
 //   Product getProductById(int id);

  //  ProductResponse getProductByIdDto(Integer id);
  //  Page<ProductResponse> listProducts(String q, String category, Double min, Double max,
                                      // int page, int size, String sort);

    Map<String, Long> getProductCountByCategory();




}
