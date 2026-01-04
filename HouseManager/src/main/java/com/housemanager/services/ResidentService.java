package com.housemanager.services;

import com.housemanager.models.Building;
import com.housemanager.models.Resident;
import com.housemanager.repositories.ResidentRepository;

import java.util.Comparator;
import java.util.List;

public class ResidentService extends BaseService<Resident> {

    public ResidentService() {
        super(new ResidentRepository(), id -> "Resident with ID " + id + " not found", "resident");
    }

    public List<Resident> getAllSortedByName() {
        return getAll().stream()
                .sorted(Comparator.comparing(Resident::getName))
                .toList();
    }

    public List<Resident> getAllSortedByAge() {
        return getAll().stream()
                .sorted(Comparator.comparingInt(Resident::getAge).reversed())
                .toList();
    }

    public List<Resident> getResidentsByBuilding(Building building) {
        return getAll().stream()
                .filter(r -> r.getApartment() != null)
                .filter(r -> r.getApartment().getBuilding().equals(building))
                .toList();
    }
}