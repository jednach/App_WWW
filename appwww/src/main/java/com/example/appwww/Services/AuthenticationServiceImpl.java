package com.example.appwww.Services;

import com.example.appwww.Dtos.LoginUserRequestDto;
import com.example.appwww.Dtos.RegisterPatientRequestDto;
import com.example.appwww.Models.Entities.*;
import com.example.appwww.Models.Enums.UserType;
import com.example.appwww.Repositories.PatientBookRepository;
import com.example.appwww.Repositories.PatientRepository;
import com.example.appwww.Repositories.RoleRepository;
import com.example.appwww.Repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl {

    @Value("${security.jwt.secret-key}")
    private String secretKey;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final EmailServiceImpl emailService;
    private final VerificationTokenServiceImpl verificationTokenService;
    private final PatientRepository patientRepository;
    private final PatientBookRepository patientBookRepository;

    @Autowired
    public AuthenticationServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository,
            AuthenticationManager authenticationManager,
            EmailServiceImpl emailService,
            VerificationTokenServiceImpl verificationTokenService,
            PatientRepository patientRepository, PatientBookRepository patientBookRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
        this.verificationTokenService = verificationTokenService;
        this.patientRepository = patientRepository;
        this.patientBookRepository = patientBookRepository;
    }

    public void signup(RegisterPatientRequestDto registerPatientRequestDto){
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setFirstName(registerPatientRequestDto.getFirstName());
        patientEntity.setLastName(registerPatientRequestDto.getLastName());
        patientEntity.setGender(patientEntity.isGender());
        patientEntity.setPeselNumber(registerPatientRequestDto.getPeselNumber());
        patientEntity.setBirthDate(registerPatientRequestDto.getBirthDate());
        patientEntity.setEmail(registerPatientRequestDto.getEmail());
        patientEntity.setPassword(passwordEncoder.encode(registerPatientRequestDto.getPassword()));
        patientEntity.setPhoneNumber(registerPatientRequestDto.getPhoneNumber());
        patientEntity.setType(UserType.PATIENT);
        patientRepository.save(patientEntity);

        PatientBookEntity patientBookEntity = new PatientBookEntity();
        patientBookEntity.setPatient(patientEntity);
        patientBookEntity.setPatientInfo(registerPatientRequestDto.getPatientInfo());
        patientBookRepository.save(patientBookEntity);

        String token = verificationTokenService.generateVerificationToken(patientEntity);
        sendVerificationEmail(patientEntity, token);
    }

    public Authentication authenticate(LoginUserRequestDto loginUserRequestDto) {
        UserEntity user = userRepository.findByEmail(loginUserRequestDto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (!user.isEnabled()) throw new RuntimeException("Account not verified. Please verify your account");
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginUserRequestDto.getEmail(),
                loginUserRequestDto.getPassword()
        ));
    }

    public void verifyUser(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        String email = claims.getSubject();
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            VerificationTokenEntity verificationToken = verificationTokenService.findByUserEmail(user.getEmail());
            if (user.isEnabled()) throw new RuntimeException("Account already verified");
            if (verificationToken.getVerificationTokenExpiresAt().isBefore(LocalDateTime.now()))
                throw new RuntimeException("Verification code has expired");
            if (verificationToken.getVerificationToken().equals(token)) {
                RoleEntity userRole = roleRepository.findByName("PATIENT").orElseThrow(
                        () -> new EntityNotFoundException("Role not found")
                );
                user.setEnabled(true);
                verificationTokenService.enableUser(verificationToken);
                user.getRoles().add(userRole);
                userRole.getUsers().add(user);
                roleRepository.save(userRole);
                userRepository.save(user);
            } else {
                throw new RuntimeException("Invalid verification code");
            }
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    public void resendVerificationCode(String email) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            if (user.isEnabled()) throw new RuntimeException("Account is already verified");
            VerificationTokenEntity verificationToken = verificationTokenService.findByUserEmail(user.getEmail());
            String token = verificationTokenService.regenerateVerificationToken(user.getEmail(), verificationToken);
            sendVerificationEmail(user, token);
            userRepository.save(user);
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }
    private void sendVerificationEmail(UserEntity user, String token) {
        String subject = "Account Verification";
        String verificationLink = "http://localhost:8080/auth/verify?token=" + token;
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif; text-align: center;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 40px;\">"
                + "<div style=\"max-width: 600px; margin: 20px auto; background-color: #fff; padding: 20px; border: 1px solid #000;\">"
                + "<h2 style=\"color: #333; border: 1px solid #000; padding: 10px;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please click the following link to verify your account:</p>"
                + "<a href=\"" + verificationLink + "\" style=\"display: inline-block; margin-top: 20px; padding: 10px 20px; border: 1px solid #000; color: #000; text-decoration: none;\">LINK</a>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        try {
            emailService.sendEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
