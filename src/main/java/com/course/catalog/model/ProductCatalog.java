package com.course.catalog.model;

import java.util.List;

public class ProductCatalog {

    private static final List<ProductDTO> PRODUCTS = List.of(
            new ProductDTO(1L, "Laptop", "electronics", 2500.0),
            new ProductDTO(2L, "Headphones", "electronics", 180.0),
            new ProductDTO(3L, "Java Book", "books", 65.0),
            new ProductDTO(4L, "Desk Lamp", "home", 40.0),
            new ProductDTO(5L, "Backpack", "accessories", 95.0)
    );

    public static List<ProductDTO> findAll() {
        return PRODUCTS;
    }

    public static ProductDTO findById(Long id) {
        return PRODUCTS.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}