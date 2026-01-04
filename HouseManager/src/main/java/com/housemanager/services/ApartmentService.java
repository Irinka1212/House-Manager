package com.housemanager.services;

import com.housemanager.models.Apartment;
import com.housemanager.repositories.ApartmentRepository;

public class ApartmentService extends BaseService<Apartment> {
    public ApartmentService() {
        super(new ApartmentRepository(), number -> "Apartment " + number + " not found", "apartment");
    }
}
