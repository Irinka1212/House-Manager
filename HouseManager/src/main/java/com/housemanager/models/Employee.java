package com.housemanager.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean active = true;

    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @OneToMany(mappedBy = "assignedEmployee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Building> buildings = new ArrayList<>();

    public Employee() {}

    public Employee(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public boolean isActive() { return active; }
    public Company getCompany() { return company; }
    public List<Building> getBuildings() { return buildings; }

    public void setCompany(Company company) {
        this.company = company;
        this.companyName = company.getName();
    }

    public void addBuilding(Building building) {
        buildings.add(building);
        building.setAssignedEmployee(this);
    }

    public void dismiss() {
        this.active = false;
    }
}