package com.eshop.eshop.repository.specs;

import com.eshop.eshop.model.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    private ProductSpecifications() {}

    public static Specification<Product> nameContains(String q) {
        return (root, query, cb) ->
                q == null ? null : cb.like(cb.lower(root.get("name")), "%" + q.toLowerCase() + "%");
    }

    public static Specification<Product> categoryEquals(String category) {
        return (root, query, cb) ->
                category == null ? null : cb.equal(cb.lower(root.get("category")), category.toLowerCase());
    }

    public static Specification<Product> priceGreaterOrEqual(Double min) {
        return (root, query, cb) ->
                min == null ? null : cb.greaterThanOrEqualTo(root.get("price"), min);
    }

    public static Specification<Product> priceLessOrEqual(Double max) {
        return (root, query, cb) ->
                max == null ? null : cb.lessThanOrEqualTo(root.get("price"), max);
    }

}
