package com.example.e_commerce.dtos;

import com.example.e_commerce.event.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class PaymentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderId;
    private String mpesaReceipt;
    private String checkOutRequestId;
    private LocalDateTime transactionDate;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private Double amount;
}
