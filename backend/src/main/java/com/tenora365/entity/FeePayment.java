package com.tenora365.entity;
import jakarta.persistence.*;import lombok.*;import java.time.*;import java.math.*;
@Entity @Table(name="fee_payments") @Getter @Setter @NoArgsConstructor
public class FeePayment{@Id @GeneratedValue(strategy=GenerationType.IDENTITY)Long id;@ManyToOne(fetch=FetchType.LAZY)@JoinColumn(name="school_id",nullable=false)School school;Long studentId;String receiptNo;BigDecimal amount;String feeHead;String status;String provider;String providerPaymentId;LocalDate paidOn;LocalDateTime createdAt=LocalDateTime.now();}
