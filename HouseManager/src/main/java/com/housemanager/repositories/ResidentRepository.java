package com.housemanager.repositories;

import com.housemanager.models.Resident;

public class ResidentRepository extends BaseRepository<Resident> {
    public ResidentRepository() { super(Resident.class); }
}