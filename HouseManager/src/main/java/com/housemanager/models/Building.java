package com.housemanager.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "buildings")
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private int floors;

    @Column(nullable = false)
    private int apartmentCount;

    @Column(nullable = false)
    private double builtArea;

    @Column(nullable = false)
    private double commonArea;

    @Column(nullable = false)
    private double feePerSqMeter = 1.5;

    @Column(nullable = false)
    private double feePerResidentOver7Elevator = 5.0;

    @Column(nullable = false)
    private double feePerPetCommonAreas = 10.0;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee assignedEmployee;

    @JoinColumn(name = "employee_name")
    private String employeeName;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Apartment> apartments = new ArrayList<>();

    public Building() {}

    public Building(String address, int floors, int apartmentCount, double commonArea) {
        this.address = address;
        this.floors = floors;
        this.apartmentCount = apartmentCount;
        this.commonArea = commonArea;
    }

    public Long getId() { return id; }
    public String getAddress() { return address; }
    public int getFloors() { return floors; }
    public int getApartmentCount() { return apartmentCount; }
    public double getBuiltArea() { return builtArea; }
    public double getCommonArea() { return commonArea; }
    public Employee getAssignedEmployee() { return assignedEmployee; }
    public List<Apartment> getApartments() { return apartments; }
    public double getFeePerSqMeter() { return feePerSqMeter; }
    public double getFeePerResidentOver7Elevator() { return feePerResidentOver7Elevator; }
    public double getFeePerPetCommonAreas() { return feePerPetCommonAreas; }

    public void setAddress(String address) { this.address = address; }
    public void setFloors(int floors) { this.floors = floors; }
    public void setApartmentCount(int apartmentCount) { this.apartmentCount = apartmentCount; }
    public void setCommonArea(double commonArea) { this.commonArea = commonArea; }

    public void setFeePerSqMeter(double feePerSqMeter) { this.feePerSqMeter = feePerSqMeter; }
    public void setFeePerResidentOver7Elevator(double feePerResidentOver7Elevator) { this.feePerResidentOver7Elevator = feePerResidentOver7Elevator; }
    public void setFeePerPetCommonAreas(double feePerPetCommonAreas) { this.feePerPetCommonAreas = feePerPetCommonAreas; }

    public void setAssignedEmployee(Employee assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
        this.employeeName = assignedEmployee != null ? assignedEmployee.getName() : null;

        if (assignedEmployee != null && !assignedEmployee.getBuildings().contains(this)) {
            assignedEmployee.getBuildings().add(this);
        }
    }

    public void addApartment(Apartment apartment) {
        if (!apartments.contains(apartment) && getApartments().size() < apartmentCount) {
            apartments.add(apartment);
            builtArea += apartment.getArea();
            apartment.setBuilding(this);
        }
    }
}