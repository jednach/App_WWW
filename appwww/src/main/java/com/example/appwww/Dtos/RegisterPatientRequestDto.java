package com.example.appwww.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPatientRequestDto {
    private String email;
    private String password;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private boolean gender;
    private String peselNumber;
    private LocalDate birthDate;
    private String patientInfo;
}
