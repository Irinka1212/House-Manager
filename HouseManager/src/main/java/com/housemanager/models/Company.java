package com.housemanager.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private double totalIncome = 0.0;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Employee> employees = new ArrayList<>();

    public Company() {}

    public Company(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public double getTotalIncome() { return totalIncome; }
    public List<Employee> getEmployees() { return employees; }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setCompany(this);
    }

    public void addIncome(double amount) {
        this.totalIncome += amount;
    }
}