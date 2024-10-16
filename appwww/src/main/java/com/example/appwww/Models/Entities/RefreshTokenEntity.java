package com.example.appwww.Models.Entities;

import com.example.appwww.Models.BaseEntityAudit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "refresh_tokens", schema = "app")
public class RefreshTokenEntity extends BaseEntityAudit {
    @Column(name = "token")
    private String token;
    @Column(name = "expiry_date")
    private Instant expiryDate;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}