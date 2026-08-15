package com.tenora365.entity;
import jakarta.persistence.*;import lombok.*;import java.time.*;import java.math.*;
@Entity @Table(name="payment_orders") @Getter @Setter @NoArgsConstructor
public class PaymentOrder{@Id @GeneratedValue(strategy=GenerationType.IDENTITY)Long id;@ManyToOne(fetch=FetchType.LAZY)@JoinColumn(name="school_id",nullable=false)School school;String orderNo;BigDecimal amount;String currency;String status;String provider;String providerOrderId;Instant createdAt=Instant.now();}
