package com.example.appwww.Controllers;

import com.example.appwww.Dtos.*;
import com.example.appwww.Models.Entities.RefreshTokenEntity;
import com.example.appwww.Models.Entities.UserEntity;
import com.example.appwww.Services.AuthenticationServiceImpl;
import com.example.appwww.Services.JwtServiceImpl;
import com.example.appwww.Services.RefreshTokenServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthenticationController {
    private final JwtServiceImpl jwtService;
    private final AuthenticationServiceImpl authenticationService;
    private final RefreshTokenServiceImpl refreshTokenService;

    @Autowired
    public AuthenticationController(
            JwtServiceImpl jwtService,
            AuthenticationServiceImpl authenticationService,
            RefreshTokenServiceImpl refreshTokenService
    ) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/singup")
    public ResponseEntity<?> registerCompetitor(
            @RequestBody RegisterUserRequestDto registerUserRequestDto) {
        authenticationService.signup(registerUserRequestDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/login")
    public LoginUserResponseDto authenticate(@RequestBody LoginUserRequestDto loginUserDto) {
        Authentication authentication = authenticationService.authenticate(loginUserDto);
        if(authentication.isAuthenticated()) {
            RefreshTokenEntity refreshToken = refreshTokenService.findByUserEmail(loginUserDto.getEmail())
                    .filter(refreshTokenService::verifyExpiration)
                    .map(refreshTokenService::renewRefreshTokenExpiryDate)
                    .orElseGet(() -> {
                        refreshTokenService.findByUserEmail(loginUserDto.getEmail())
                                .ifPresent(refreshTokenService::deleteRefreshToken);
                        return refreshTokenService.createRefreshToken(loginUserDto.getEmail());
                    });
            return new LoginUserResponseDto(
                    jwtService.generateToken(loginUserDto.getEmail()),
                    refreshToken.getToken()
            );
        } else {
            throw new UsernameNotFoundException("Invalid user");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginUserResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        RefreshTokenEntity refreshToken = refreshTokenService.findByToken(refreshTokenRequestDto.getRefreshToken())
                .orElseThrow(() -> new EntityNotFoundException("Invalid refresh token"));
        if(refreshTokenService.verifyExpiration(refreshToken)) {
            return ResponseEntity.ok(
                    new LoginUserResponseDto(
                            jwtService.generateToken(refreshToken.getUser().getEmail()),
                            refreshToken.getToken()
                    )
            );
        } else {
            refreshTokenService.deleteRefreshToken(refreshToken);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam("token") String token){
        authenticationService.verifyUser(token);
        return ResponseEntity.ok("Account verified successfully");
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(
            @RequestBody ResendVerificationEmailRequestDto resendVerificationEmailRequestDto) {
        authenticationService.resendVerificationCode(resendVerificationEmailRequestDto.getEmail());
        return ResponseEntity.ok("Verification code sent");
    }
}
