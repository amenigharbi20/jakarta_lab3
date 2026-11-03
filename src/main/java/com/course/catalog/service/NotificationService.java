package com.course.catalog.service;

import jakarta.ejb.Singleton;

@Singleton
public class NotificationService {

    public void logOrderConfirmation(String customerName) {
        System.out.println("[NOTIFY] Order confirmed for: " + customerName);
    }
}