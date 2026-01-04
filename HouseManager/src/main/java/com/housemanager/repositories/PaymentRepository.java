package com.housemanager.repositories;

import com.housemanager.models.Payment;

public class PaymentRepository extends BaseRepository<Payment> {
    public PaymentRepository() { super(Payment.class); }
}