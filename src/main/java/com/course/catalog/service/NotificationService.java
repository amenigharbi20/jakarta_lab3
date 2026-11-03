package com.course.catalog.service;

import jakarta.ejb.Lock;
import jakarta.ejb.LockType;
import jakarta.ejb.Singleton;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * @Singleton = ONE instance for the whole application, shared by all users,
 * so the history survives between different users' checkouts.
 * A @SessionScoped or @Stateless bean would not guarantee that.
 */
@Singleton
public class NotificationService {

    private static final int MAX_HISTORY = 5;
    private final LinkedList<String> history = new LinkedList<>();

    @Lock(LockType.WRITE)
    public void logOrderConfirmation(String customerName) {
        System.out.println("[NOTIFY] Order confirmed for: " + customerName);
        history.addFirst(customerName + " - " + LocalDateTime.now());
        if (history.size() > MAX_HISTORY) {
            history.removeLast();
        }
    }

    @Lock(LockType.READ)
    public List<String> getHistory() {
        return List.copyOf(history);
    }
}