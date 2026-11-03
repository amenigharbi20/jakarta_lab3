package com.course.catalog.service;

import jakarta.ejb.ApplicationException;

// rollback = true : le conteneur annule la transaction quand cette exception est levee.
// Sans @ApplicationException, le conteneur EJB l'envelopperait dans une EJBException.
@ApplicationException(rollback = true)
public class EmptyCartException extends RuntimeException {
    public EmptyCartException(String message) {
        super(message);
    }
}