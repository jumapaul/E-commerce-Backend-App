package com.example.e_commerce.repository;

import com.example.e_commerce.dtos.PaymentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentsEntity, Long> {

    Optional<PaymentsEntity> findByCheckOutRequestId(String checkOutRequestId);
}
