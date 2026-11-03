package com.course.catalog.service;

import com.course.catalog.model.ProductDTO;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

/**
 * Design choice: PromotionService is a SEPARATE service that depends on CartService,
 * not a method on CartService. Single responsibility: CartService only knows WHICH
 * items are in the cart. Pricing rules (discounts, promo codes) change often and
 * are business policy, so they live in their own bean. It is stateless: it holds
 * no data of its own and just reads the user's cart.
 */
@Stateless
public class PromotionService {

    private static final double DISCOUNT_RATE = 0.10;

    @Inject
    private CartService cartService;

    public double getSubtotal() {
        return cartService.getItems().stream()
                .mapToDouble(ProductDTO::getPrice)
                .sum();
    }

    public double getDiscount() {
        return getSubtotal() * DISCOUNT_RATE;
    }

    public double getTotalAfterDiscount() {
        return getSubtotal() - getDiscount();
    }
}