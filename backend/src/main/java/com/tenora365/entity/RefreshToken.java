package com.tenora365.entity;
import jakarta.persistence.*;import lombok.*;import java.time.*;
@Entity @Table(name="refresh_tokens") @Getter @Setter @NoArgsConstructor
public class RefreshToken{@Id @GeneratedValue(strategy=GenerationType.IDENTITY)Long id;@ManyToOne(fetch=FetchType.LAZY)@JoinColumn(name="user_id",nullable=false)User user;@Column(nullable=false,unique=true)String tokenHash;Instant expiresAt;boolean revoked=false;Instant createdAt=Instant.now();}
