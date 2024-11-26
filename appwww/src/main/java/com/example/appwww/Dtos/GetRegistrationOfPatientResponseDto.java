package com.example.appwww.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetRegistrationOfPatientResponseDto {
    private Long registrationId;
    private Long doctorId;
    private LocalDate date;
    private LocalTime time;
}
