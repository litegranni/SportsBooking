package com.example.sportsbooking.exceptions;

/**
 * Specialiserat undantag för när en resurs inte hittas.
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
