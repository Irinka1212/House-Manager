package com.housemanager.exceptions;

public class EntityUpdateException extends Exception {
    public EntityUpdateException(String message) { super("Cannot update " + message); }
}
