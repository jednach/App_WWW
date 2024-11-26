package com.example.appwww.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetRegistrationForDoctorResponseDto {
    private Long registrationId;
    private Long patientId;
    private LocalTime registrationTime;
}
