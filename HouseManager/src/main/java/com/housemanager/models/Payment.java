package com.housemanager.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @JoinColumn(name = "company_name", nullable = false)
    private String companyName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;

    @JoinColumn(name = "apartment_number", nullable = false)
    private String apartmentNumber;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    public Payment() { }

    public Payment(Company company, Apartment apartment, double amount) {
        this.company = company;
        this.companyName = company.getName();
        this.apartment = apartment;
        this.apartmentNumber = apartment.getNumber();
        this.amount = amount;
        this.paymentDate = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Company getCompany() { return company; }
    public Apartment getApartment() { return apartment; }
    public double getAmount() { return amount; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
}