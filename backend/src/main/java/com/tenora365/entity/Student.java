package com.tenora365.entity;
import jakarta.persistence.*;import lombok.*;import java.time.*;
@Entity @Table(name="students") @Getter @Setter @NoArgsConstructor
public class Student{@Id @GeneratedValue(strategy=GenerationType.IDENTITY)Long id;@ManyToOne(fetch=FetchType.LAZY)@JoinColumn(name="school_id",nullable=false)@com.fasterxml.jackson.annotation.JsonIgnore School school;String admissionNo;String fullName;String className;String section;String rollNo;String gender;LocalDate dob;String parentName;String parentPhone;String photoUrl;boolean active=true;LocalDateTime createdAt=LocalDateTime.now();}
