package com.example.appwww.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetDoctorWorkingHoursRequestDto {
    private DayOfWeek dayOfWeek;
}
