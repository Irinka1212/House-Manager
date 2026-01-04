package com.housemanager.models;

import jakarta.persistence.*;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean usesCommonAreas;

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    @JoinColumn(name = "apartment_number")
    private String apartmentNumber;

    public Pet() { }

    public Pet(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public boolean usesCommonAreas() { return getApartment().getBuilding().getCommonArea() > 0; }
    public Apartment getApartment() { return apartment; }

    public void setName(String name) { this.name = name; }
    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
        this.apartmentNumber = apartment.getNumber();
    }
}