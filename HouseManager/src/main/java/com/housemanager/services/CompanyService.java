package com.housemanager.services;

import com.housemanager.models.Company;
import com.housemanager.repositories.CompanyRepository;

import java.util.Comparator;
import java.util.List;

public class CompanyService extends BaseService<Company> {
    public CompanyService() {
        super(new CompanyRepository(), id -> "Company with ID " + id + " not found", "company");
    }

    public List<Company> getAllSortedByIncome() {
        return getAll().stream()
                .sorted(Comparator.comparingDouble(Company::getTotalIncome).reversed())
                .toList();
    }
}