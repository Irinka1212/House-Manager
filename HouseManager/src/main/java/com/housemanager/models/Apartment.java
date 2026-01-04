package com.housemanager.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "apartments")
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private double area;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @JoinColumn(name = "building_address")
    private String buildingAddress;

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL)
    private List<Resident> residents = new ArrayList<>();

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL)
    private List<Pet> pets = new ArrayList<>();

    public Apartment() { }

    public Apartment(String number, double area, Building building) {
        this.number = number;
        this.area = area;
        this.building = building;
        this.buildingAddress = building.getAddress();
    }

    public Long getId() { return id; }
    public String getNumber() { return number; }
    public double getArea() { return area; }
    public Building getBuilding() { return building; }
    public List<Resident> getResidents() { return residents; }
    public List<Pet> getPets() { return pets; }

    public void setNumber(String number) { this.number = number; }
    public void setArea(double area) { this.area = area; }
    public void setBuilding(Building building) {
        this.building = building;
        this.buildingAddress = building.getAddress();
    }

    public void addResident(Resident resident) {
        residents.add(resident);
        resident.setApartment(this);
    }

    public void addPet(Pet pet) {
        pets.add(pet);
        pet.setApartment(this);
    }
}