package com.course.catalog.service;

import com.course.catalog.model.ProductDTO;
import jakarta.ejb.Stateful;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateful
@SessionScoped
public class CartService implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<ProductDTO> items = new ArrayList<>();

    public void addItem(ProductDTO product) {
        items.add(product);
    }

    public void removeItem(Long productId) {
        items.removeIf(p -> p.getId().equals(productId));
    }

    // Suppression par position, comme au Lab 2 (le champ "index" de cart.jsp)
    public void removeAt(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
    }

    public List<ProductDTO> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }
}