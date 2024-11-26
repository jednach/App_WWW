package com.example.appwww.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPatientResponseDto {
    private Long patientId;
    private Long patientBookId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private boolean gender;
    private String phoneNumber;
    private String peselNumber;
}
