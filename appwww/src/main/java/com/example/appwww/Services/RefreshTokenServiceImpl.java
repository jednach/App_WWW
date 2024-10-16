package com.example.appwww.Services;

import com.example.appwww.Models.Entities.RefreshTokenEntity;
import com.example.appwww.Models.Entities.UserEntity;
import com.example.appwww.Repositories.RefreshTokenRepository;
import com.example.appwww.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl {

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    @Autowired
    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }


    public RefreshTokenEntity createRefreshToken(String username) {
        UserEntity user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User with email:" + username + " not found"));

        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(1209600000)); //14 days
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public Optional<RefreshTokenEntity> findByUserEmail(String email) {
        return refreshTokenRepository.findByUserEmail(email);
    }

    public boolean verifyExpiration(RefreshTokenEntity token) {
        return token.getExpiryDate().compareTo(Instant.now()) > 0;
    }

    public RefreshTokenEntity renewRefreshTokenExpiryDate(RefreshTokenEntity refreshToken) {
        refreshToken.setExpiryDate(Instant.now().plusMillis(1209600000)); // 14 days
        return refreshTokenRepository.save(refreshToken);
    }

    public void deleteRefreshToken(RefreshTokenEntity token) {
        refreshTokenRepository.delete(token);
    }
}
