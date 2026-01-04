package com.housemanager;

import com.housemanager.exceptions.BuildingAlreadyAssignedException;
import com.housemanager.exceptions.EntityCreationException;
import com.housemanager.exceptions.EntityUpdateException;
import com.housemanager.exceptions.PaymentException;
import com.housemanager.models.*;
import com.housemanager.services.*;

public class Main {

    public static void main(String[] args) throws EntityUpdateException, EntityCreationException, BuildingAlreadyAssignedException, PaymentException {

        CompanyService companyService = new CompanyService();
        EmployeeService employeeService = new EmployeeService();
        BuildingService buildingService = new BuildingService();
        ApartmentService apartmentService = new ApartmentService();
        ResidentService residentService = new ResidentService();
        PetService petService = new PetService();
        PaymentService paymentService = new PaymentService();
        
        ReportService reportService = new ReportService(employeeService, buildingService, residentService, paymentService);

        buildingService.setEmployeeService(employeeService);
        paymentService.setCompanyService(companyService);
        employeeService.setBuildingService(buildingService);

        Company c1 = new Company("Hey Plus");
        Company c2 = new Company("Sofia Services");
        companyService.create(c1);
        companyService.create(c2);

        Employee e1 = new Employee("Ivan Ivanov");
        Employee e2 = new Employee("Maria Petrova");
        Employee e3 = new Employee("Petar Dimitrov");
        c1.addEmployee(e1);
        c2.addEmployee(e2);
        c2.addEmployee(e3);

        employeeService.create(e1);
        employeeService.create(e2);
        employeeService.create(e3);

        Employee e4 = new Employee("Nikolay Nikolov");
        Employee e5 = new Employee("Elena Georgieva");
        Employee e6 = new Employee("Dimitar Ivanov");

        c1.addEmployee(e4);
        c2.addEmployee(e5);
        c2.addEmployee(e6);

        employeeService.create(e4);
        employeeService.create(e5);
        employeeService.create(e6);


        Building b1 = new Building("Sofia, Main Street 10", 6, 2, 50);
        Building b2 = new Building("Sofia, Main Street 12", 5, 2, 40);
        Building b3 = new Building("Sofia, Central 5", 8, 2, 60);
        Building b4 = new Building("Sofia, Central 7", 7, 2, 55);

        buildingService.create(b1);
        buildingService.create(b2);
        buildingService.create(b3);
        buildingService.create(b4);

        buildingService.assignEmployee(b1, e1);
        buildingService.assignEmployee(b2, e2);
        buildingService.assignEmployee(b3, e3);
        buildingService.assignEmployee(b4, e4);

        Apartment a1 = new Apartment("1A", 80, b1);
        Apartment a2 = new Apartment("1B", 75, b1);
        Apartment a3 = new Apartment("2A", 90, b2);
        Apartment a4 = new Apartment("2B", 70, b2);

        apartmentService.create(a1);
        apartmentService.create(a2);
        apartmentService.create(a3);
        apartmentService.create(a4);

        b1.addApartment(a1);
        b1.addApartment(a2);
        b2.addApartment(a3);
        b2.addApartment(a4);

        Resident r1 = new Resident("Georgi Georgiev", 35, true);
        Resident r2 = new Resident("Anna Georgieva", 10, true);
        Resident r3 = new Resident("Ivan Petrov", 40, false);
        Resident r4 = new Resident("Elena Petrova", 12, true);

        residentService.create(r1);
        residentService.create(r2);
        residentService.create(r3);
        residentService.create(r4);

        a1.addResident(r1);
        a1.addResident(r2);
        a2.addResident(r3);
        a2.addResident(r4);

        Pet p1 = new Pet("Doggo");
        Pet p2 = new Pet("Kitty");

        a1.addPet(p1);
        a2.addPet(p2);

        System.out.println("Paying monthly fee...");
        paymentService.payFee(c1, a1);
        paymentService.payFee(c1, a2);
        paymentService.payFee(c1, a3);
        paymentService.payFee(c1, a4);

        System.out.println("\nEmployees sorted by name:");
        employeeService.getAllSortedByName()
                .forEach(e -> System.out.println(e.getName()));

        System.out.println("\nCompanies sorted by income:");
        companyService.getAllSortedByIncome()
                .forEach(c ->
                        System.out.println(c.getName() + " -> " + c.getTotalIncome()));

        System.out.println("\nActive employees soted by name:");
        employeeService.getAllSortedByName()
                .forEach(e ->
                        System.out.println(e.getName()));

        System.out.println("\nActive employees with sorting building count:");
        employeeService.getAllSortedByBuildingCount()
                .forEach(e ->
                        System.out.println(e.getName() + " -> "  + e.getBuildings().size()));

        System.out.println("\n--- Buildings per Employee ---");
        employeeService.getAll().forEach(reportService::reportBuildingsPerEmployee);

        System.out.println("\n--- Residents in Building ---");
        reportService.reportResidentsInBuilding(b1);
        reportService.reportResidentsInBuilding(b2);

        System.out.println("\n--- Paid sums per Company ---");
        companyService.getAll().forEach(reportService::reportPaidSumsByCompany);

        System.out.println("\n--- Dismissing employee Ivan Ivanov ---");
        employeeService.dismissEmployeeAndRedistributeBuildings(e1);

        employeeService.getAll().forEach(reportService::reportBuildingsPerEmployee);

        System.out.println("\nDONE — data persisted, payments logged.");
    }
}