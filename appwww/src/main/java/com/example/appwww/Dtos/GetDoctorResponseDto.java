package com.example.appwww.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetDoctorResponseDto {
    private Long doctorId;
    private String doctorFirstName;
    private String doctorLastName;
}
