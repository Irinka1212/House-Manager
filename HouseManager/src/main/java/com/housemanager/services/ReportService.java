package com.housemanager.services;

import com.housemanager.models.Building;
import com.housemanager.models.Company;
import com.housemanager.models.Employee;

public class ReportService {

    private final EmployeeService employeeService;
    private final BuildingService buildingService;
    private final ResidentService residentService;
    private final PaymentService paymentService;

    public ReportService(
            EmployeeService employeeService,
            BuildingService buildingService,
            ResidentService residentService,
            PaymentService paymentService
    ) {
        this.employeeService = employeeService;
        this.buildingService = buildingService;
        this.residentService = residentService;
        this.paymentService = paymentService;
    }

    public void reportBuildingsPerEmployee(Employee employee) {
        System.out.println(
                employee.getName() + " serves " +
                        employeeService.getBuildingCountByEmployee(employee) +
                        " buildings"
        );

        employeeService.getBuildingsByEmployee(employee)
                .forEach(b -> System.out.println(" - " + b.getAddress()));
    }

    public void reportResidentsInBuilding(Building building) {
        System.out.println("Residents in building " + building.getAddress());
        residentService.getResidentsByBuilding(building)
                .forEach(r -> System.out.println(" - " + r.getName()));
    }

    public void reportPaidSumsByCompany(Company company) {
        System.out.println(
                "Total paid to company " + company.getName() + ": " +
                        paymentService.getTotalDueByCompany(company)
        );
    }
}

