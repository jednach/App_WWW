package com.example.appwww.Services;

import com.example.appwww.Models.Entities.UserEntity;
import com.example.appwww.Models.Entities.VerificationTokenEntity;
import com.example.appwww.Repositories.VerificationTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VerificationTokenServiceImpl {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    private final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public String generateVerificationToken(UserEntity user) {
        String token = verificationTokenCreator(user.getEmail());
        VerificationTokenEntity verificationToken = new VerificationTokenEntity();
        verificationToken.setVerificationToken(token);
        verificationToken.setVerificationTokenExpiresAt(LocalDateTime.now().plusHours(1));
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public String regenerateVerificationToken(String userEmail, VerificationTokenEntity verificationToken) {
        String token = verificationTokenCreator(userEmail);
        verificationToken.setVerificationToken(token);
        verificationToken.setVerificationTokenExpiresAt(LocalDateTime.now().plusHours(1));
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public VerificationTokenEntity findByUserEmail(String email){
        return verificationTokenRepository.findByUserEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
    }

    public void enableUser(VerificationTokenEntity verificationToken){
        verificationToken.setVerificationToken(null);
        verificationToken.setVerificationTokenExpiresAt(null);
        verificationTokenRepository.save(verificationToken);
    }

    public void enableUserCreatedByAdmin(UserEntity user){
        VerificationTokenEntity verificationToken = new VerificationTokenEntity();
        verificationToken.setUser(user);
        verificationToken.setVerificationToken(null);
        verificationToken.setVerificationTokenExpiresAt(null);
        verificationTokenRepository.save(verificationToken);
    }

    private String verificationTokenCreator(String email){
        String token = Jwts.builder()
                .setSubject(email)
                .claim("token", UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return token;
    }
}