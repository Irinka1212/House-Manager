package com.housemanager.services;

import com.housemanager.exceptions.BuildingAlreadyAssignedException;
import com.housemanager.exceptions.EntityUpdateException;
import com.housemanager.models.Building;
import com.housemanager.models.Employee;
import com.housemanager.repositories.EmployeeRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EmployeeService extends BaseService<Employee> {
    private BuildingService buildingService;

    public EmployeeService() {
        super(new EmployeeRepository(), id -> "Employee with id " + id + " not found", "employee");
    }

    public void setBuildingService(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    public List<Employee> getAllSortedByName() {
        return getAll().stream()
                .filter(Employee::isActive)
                .sorted(Comparator.comparing(Employee::getName))
                .toList();
    }

    public List<Employee> getAllSortedByBuildingCount() {
        return getAll().stream()
                .filter(Employee::isActive)
                .sorted(Comparator.comparingInt(e -> e.getBuildings().size()))
                .toList();
    }

    public Employee getEmployeeWithLeastBuildings(List<Employee> employees) {
        return employees.stream()
                .filter(Employee::isActive)
                .min(Comparator.comparingInt(e -> e.getBuildings().size()))
                .orElseThrow();
    }

    public List<Building> getBuildingsByEmployee(Employee employee) {
        return employee.getBuildings();
    }

    public int getBuildingCountByEmployee(Employee employee) {
        return employee.getBuildings().size();
    }

    public void dismissEmployeeAndRedistributeBuildings(Employee employee) throws EntityUpdateException, BuildingAlreadyAssignedException {

        List<Building> buildingsToReassign = new ArrayList<>(employee.getBuildings());

        employee.dismiss();

        List<Employee> remainingEmployees =
                employee.getCompany()
                        .getEmployees()
                        .stream()
                        .filter(e -> e.isActive() && !e.getId().equals(employee.getId()))
                        .toList();

        for (Building building : buildingsToReassign) {
            this.buildingService.unassignEmployee(building);
            this.buildingService.assignBuildingToEmployeeWithLeastBuildings(building, remainingEmployees);
        }

        update(employee);
    }
}