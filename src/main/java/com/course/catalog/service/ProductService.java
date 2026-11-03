package com.course.catalog.service;

import com.course.catalog.model.ProductDTO;
import com.course.catalog.model.ProductCatalog;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class ProductService {

    public List<ProductDTO> getAllProducts() {
        return ProductCatalog.findAll();
    }

    public List<ProductDTO> getByCategory(String category) {
        return ProductCatalog.findAll().stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    public ProductDTO getProduct(Long id) {
        return ProductCatalog.findById(id);
    }
}