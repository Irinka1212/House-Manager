package com.housemanager.repositories;

import com.housemanager.models.Pet;

public class PetRepository extends BaseRepository<Pet> {
    public PetRepository() { super(Pet.class); }
}