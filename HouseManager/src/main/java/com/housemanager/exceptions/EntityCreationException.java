package com.housemanager.exceptions;

public class EntityCreationException extends Exception {
    public EntityCreationException(String message) {
        super("Cannot create " + message);
    }
}
