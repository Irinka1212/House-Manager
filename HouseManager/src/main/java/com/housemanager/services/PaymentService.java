package com.housemanager.services;

import com.housemanager.exceptions.EntityCreationException;
import com.housemanager.exceptions.EntityUpdateException;
import com.housemanager.exceptions.PaymentException;
import com.housemanager.models.*;
import com.housemanager.repositories.PaymentRepository;
import com.housemanager.util.FileUtil;

import java.time.YearMonth;

public class PaymentService extends BaseService<Payment> {
    private CompanyService companyService;

    public PaymentService() {
        super(new PaymentRepository(), id -> "Payment with ID " + id + " not found", "payment");
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public double getTotalDueByCompany(Company company) {
        return getAll().stream()
                .filter(p -> p.getCompany().equals(company))
                .mapToDouble(Payment::getAmount)
                .sum();
    }

    public double getTotalDueByBuilding(Building building) {
        return getAll().stream()
                .filter(p -> p.getApartment().getBuilding().equals(building))
                .mapToDouble(Payment::getAmount)
                .sum();
    }

    public double getTotalDueByEmployee(Employee employee) {
        return getAll().stream()
                .filter(p -> p.getApartment().getBuilding().getAssignedEmployee().equals(employee))
                .mapToDouble(Payment::getAmount)
                .sum();
    }

    public double payFee(Company company, Apartment apartment) throws EntityUpdateException, EntityCreationException, PaymentException {

        YearMonth currentMonth = YearMonth.now();
        boolean alreadyPaid = getAll().stream()
                .filter(p -> p.getApartment().equals(apartment))
                .anyMatch(p -> YearMonth.from(p.getPaymentDate()).equals(currentMonth));

        if (alreadyPaid) {
            throw new PaymentException("Fee already paid for apartment " + apartment.getNumber() + " for " + currentMonth);
        }

        if (this.companyService == null) {
            throw new PaymentException("CompanyService must be set before paying fee.");
        }

        double fee = apartment.getArea() * apartment.getBuilding().getFeePerSqMeter();

        for (var resident : apartment.getResidents()) {
            if (resident.getAge() > 7 && resident.isUsesElevator()) {
                fee += apartment.getBuilding().getFeePerResidentOver7Elevator();
            }
        }

        for (var pet : apartment.getPets()) {
            if (pet.usesCommonAreas()) {
                fee += apartment.getBuilding().getFeePerPetCommonAreas();
            }
        }

        Payment payment = new Payment(company, apartment, fee);
        create(payment);
        company.addIncome(fee);
        this.companyService.update(company);

        FileUtil.logPayment(
                company.getName(),
                apartment.getBuilding().getAssignedEmployee().getName(),
                apartment.getBuilding().getAddress(),
                apartment.getNumber(),
                fee
        );

        return fee;
    }
}