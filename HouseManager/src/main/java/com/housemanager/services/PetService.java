package com.housemanager.services;

import com.housemanager.models.Pet;
import com.housemanager.repositories.PetRepository;

public class PetService extends BaseService<Pet> {

    public PetService() {
        super(new PetRepository(), name -> "Pet " + name + " not found", "pet");
    }
}