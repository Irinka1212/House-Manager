package com.housemanager.services;

import com.housemanager.exceptions.BuildingAlreadyAssignedException;
import com.housemanager.exceptions.BuildingAssignException;
import com.housemanager.exceptions.EntityUpdateException;
import com.housemanager.models.Apartment;
import com.housemanager.models.Building;
import com.housemanager.models.Employee;
import com.housemanager.repositories.BuildingRepository;

import java.util.List;

public class BuildingService extends BaseService<Building> {
    private EmployeeService employeeService;

    public BuildingService() {
        super(new BuildingRepository(), id -> "Building with ID " + id + " not found", "building");
    }

    public List<Apartment> getApartmentsInBuilding(Building building) {
        return building.getApartments();
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void assignEmployee(Building building, Employee employee) throws BuildingAlreadyAssignedException, EntityUpdateException {
        if (building.getAssignedEmployee() != null) {
            throw new BuildingAlreadyAssignedException(building.getAddress());
        }
        building.setAssignedEmployee(employee);
        employee.addBuilding(building);

        update(building);
    }

    public void unassignEmployee(Building building) throws EntityUpdateException {
        if (building.getAssignedEmployee() != null) {
            building.getAssignedEmployee()
                    .getBuildings()
                    .remove(building);
        }
        building.setAssignedEmployee(null);
        update(building);
    }

    public void assignBuildingToEmployeeWithLeastBuildings(Building newBuilding, List<Employee> availableEmployees) throws EntityUpdateException, BuildingAlreadyAssignedException {
        if (availableEmployees.isEmpty()) {
            throw new BuildingAssignException("Cannot assign building: No employees available.");
        }

        if (this.employeeService == null) {
            throw new BuildingAssignException("EmployeeService must be set before assigning buildings.");
        }

        Employee employee = this.employeeService.getEmployeeWithLeastBuildings(availableEmployees);

        assignEmployee(newBuilding, employee);
        employee.addBuilding(newBuilding);
    }
}