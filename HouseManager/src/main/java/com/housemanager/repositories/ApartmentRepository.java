package com.housemanager.repositories;

import com.housemanager.models.Apartment;

public class ApartmentRepository extends BaseRepository<Apartment> {
    public ApartmentRepository() { super(Apartment.class); }
}