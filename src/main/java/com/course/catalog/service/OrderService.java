package com.course.catalog.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Stateless
public class OrderService {

    @Inject
    private NotificationService notificationService;

    @Inject
    private CartService cartService;

    @Transactional
    public void createOrder(String name, String email, String address) {
        // Garde : on refuse la commande si le panier est vide, AVANT toute notification.
        if (cartService.getItems().isEmpty()) {
            throw new EmptyCartException("Cannot place an order with an empty cart.");
        }
        // Le chapitre 4 enregistrera la commande en base ici.
        notificationService.logOrderConfirmation(name);
        cartService.clear();
    }
}