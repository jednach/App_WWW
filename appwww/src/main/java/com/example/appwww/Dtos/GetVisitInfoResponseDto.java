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
public class GetVisitInfoResponseDto {
    private Long visitId;
    private Long doctorId;
    private LocalDate visitDate;
    private String description;
}
