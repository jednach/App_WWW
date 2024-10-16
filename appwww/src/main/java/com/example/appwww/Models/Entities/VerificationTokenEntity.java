package com.example.appwww.Models.Entities;

import com.example.appwww.Models.BaseEntityAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "verification_tokens", schema = "app")
public class VerificationTokenEntity extends BaseEntityAudit {
    @Column(name = "token")
    private String verificationToken;

    @Column(name = "expiry_date")
    private LocalDateTime verificationTokenExpiresAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
