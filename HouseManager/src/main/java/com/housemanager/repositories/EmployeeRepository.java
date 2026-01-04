package com.housemanager.repositories;

import com.housemanager.models.Employee;

public class EmployeeRepository extends BaseRepository<Employee> {
    public EmployeeRepository() { super(Employee.class); }
}