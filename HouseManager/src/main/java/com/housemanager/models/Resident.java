package com.housemanager.models;

import jakarta.persistence.*;

@Entity
@Table(name = "residents")
public class Resident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private boolean usesElevator;

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    @JoinColumn(name = "apartment_number")
    private String apartmentNumber;

    public Resident() { }

    public Resident(String name, int age, boolean usesElevator) {
        this.name = name;
        this.age = age;
        this.usesElevator = usesElevator;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public boolean isUsesElevator() { return usesElevator; }
    public Apartment getApartment() { return apartment; }

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setUsesElevator(boolean usesElevator) { this.usesElevator = usesElevator; }
    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
        this.apartmentNumber = apartment.getNumber();
    }
}