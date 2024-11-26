package com.example.appwww.Dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GetPatientRequestDto {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String peselNumber;
    private Boolean gender = null;
    private int pageNumber = 0;
}
