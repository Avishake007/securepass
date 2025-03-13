package com.securepass.PaymentService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.securepass.PaymentService.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
