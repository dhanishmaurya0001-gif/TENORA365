package com.tenora365.entity;
import jakarta.persistence.*;import lombok.*;import java.time.*;import java.math.*;
@Entity @Table(name="teachers") @Getter @Setter @NoArgsConstructor
public class Teacher{@Id @GeneratedValue(strategy=GenerationType.IDENTITY)Long id;@ManyToOne(fetch=FetchType.LAZY)@JoinColumn(name="school_id",nullable=false)@com.fasterxml.jackson.annotation.JsonIgnore School school;String employeeNo;String fullName;String subject;String phone;String email;BigDecimal salary=BigDecimal.ZERO;boolean active=true;LocalDateTime createdAt=LocalDateTime.now();}
