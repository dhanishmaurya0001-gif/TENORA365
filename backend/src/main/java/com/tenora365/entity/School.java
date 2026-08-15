package com.tenora365.entity;
import jakarta.persistence.*;import lombok.*;import java.time.*;
@Entity @Table(name="schools") @Getter @Setter @NoArgsConstructor
public class School{@Id @GeneratedValue(strategy=GenerationType.IDENTITY)Long id;@Column(nullable=false)String name;@Column(nullable=false,unique=true)String code;String email;String phone;String address;boolean active=true;LocalDateTime createdAt=LocalDateTime.now();}
