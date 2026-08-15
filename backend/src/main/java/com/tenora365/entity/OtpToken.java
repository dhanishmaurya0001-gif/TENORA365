package com.tenora365.entity;
import jakarta.persistence.*;import lombok.*;import java.time.*;
@Entity @Table(name="otp_tokens") @Getter @Setter @NoArgsConstructor
public class OtpToken{@Id @GeneratedValue(strategy=GenerationType.IDENTITY)Long id;@ManyToOne(fetch=FetchType.LAZY)@JoinColumn(name="user_id",nullable=false)User user;String otpHash;Instant expiresAt;boolean used=false;Instant createdAt=Instant.now();}
