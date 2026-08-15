package com.tenora365.entity;
import jakarta.persistence.*;import lombok.*;import java.time.*;
@Entity @Table(name="users") @Getter @Setter @NoArgsConstructor
public class User{@Id @GeneratedValue(strategy=GenerationType.IDENTITY)Long id;@ManyToOne(fetch=FetchType.LAZY)@JoinColumn(name="school_id")School school;@Column(nullable=false,unique=true)String username;@Column(name="password_hash",nullable=false)String passwordHash;String fullName;@Column(nullable=false)String role;boolean enabled=true;LocalDateTime createdAt=LocalDateTime.now();}
