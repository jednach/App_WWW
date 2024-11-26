package com.example.appwww.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetPatientBookResponseDto {
    private Long patientBookId;
    private String patientFirstName;
    private String patientLastName;
    private String peselNumber;
    private LocalDate birthDate;
    private boolean gender;
    private String patientInfo;
    private List<GetVisitInfoResponseDto> visits;
}
