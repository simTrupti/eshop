package com.eshop.eshop.repository;

import com.eshop.eshop.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    Optional<Product> findByName(String name);

    Optional<Product> findById( Integer id);

    List<Product> findAll();


}
