package com.tenora365.entity;
import jakarta.persistence.*;import lombok.*;import java.time.*;import java.math.*;
@Entity @Table(name="subscriptions") @Getter @Setter @NoArgsConstructor
public class Subscription{@Id @GeneratedValue(strategy=GenerationType.IDENTITY)Long id;@ManyToOne(fetch=FetchType.LAZY)@JoinColumn(name="school_id",nullable=false)School school;String planCode;String status;String billingCycle;BigDecimal amount;Instant startsAt;Instant endsAt;String providerSubscriptionId;Instant createdAt=Instant.now();}
