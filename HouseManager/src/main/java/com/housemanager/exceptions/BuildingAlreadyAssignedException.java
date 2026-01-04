package com.housemanager.exceptions;

public class BuildingAlreadyAssignedException extends Exception {
    public BuildingAlreadyAssignedException(String address) {
        super("Building already assigned: " + address);
    }
}