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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

@Service
 public class ProductServiceImpl implements ProductService {

   // List<Product> products = new ArrayList<>();
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(Product product){

        //normalise names
        product.setName(product.getName().toLowerCase());

        //check duplicate
        if(productRepository.findByName(product.getName()).isPresent()){
            throw new IllegalArgumentException("Product already exists");

        }

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

//    @Override
//    public Product getProductById(int id){
//        for(Product product: products) {
//            if (product.getId() == id) {
//                return product;
//            }
//
//        }
//        return null;
//        Optional<Product> opt = productRepository.findById(id);
//        return opt.orElse(null);
//    }

    @Override
    public ProductResponse getProductByIdDto(Integer id){
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setQuantity(product.getQuantity());
        response.setCategory(product.getCategory());
        response.setCreateAt(product.getCreatedAt());
        response.setUpdateAt(product.getUpdatedAt());

        response.setAverageRating(null);

        return response;
    }

    // helper to parse "field,dir" to Sort
    private Sort parseSort(String sort) {
        if (sort == null || sort.isBlank()) return Sort.by("id").ascending();
        String[] parts = sort.split(",");
        if (parts.length == 1) return Sort.by(parts[0]).ascending();
        return "desc".equalsIgnoreCase(parts[1]) ? Sort.by(parts[0]).descending() : Sort.by(parts[0]).ascending();
    }

    // mapping function Product -> ProductResponse
    private final Function<Product, ProductResponse> toResponse = product -> {
        ProductResponse r = new ProductResponse();
        r.setId(product.getId());
        r.setName(product.getName());
        r.setDescription(product.getDescription());
        r.setPrice(product.getPrice());
        r.setQuantity(product.getQuantity());
        r.setCategory(product.getCategory());
        r.setCreateAt(product.getCreatedAt());
        r.setUpdateAt(product.getUpdatedAt());
        r.setAverageRating(null); // compute later if reviews exist
        return r;
    };

    @Override
    public Page<ProductResponse> listProducts(String q, String category, Double min, Double max,
                                              int page, int size, String sort) {

        Sort sortObj = parseSort(sort);
        Pageable pageable = PageRequest.of(page, size, sortObj);

        // Build combined specification
        Specification<Product> spec = Specification.where(ProductSpecifications.nameContains(q))
                .and(ProductSpecifications.categoryEquals(category))
                .and(ProductSpecifications.priceGreaterOrEqual(min))
                .and(ProductSpecifications.priceLessOrEqual(max));

        Page<Product> productPage = productRepository.findAll(spec, pageable);

        // Map Page<Product> to Page<ProductResponse>
        return productPage.map(toResponse);
    }

}
