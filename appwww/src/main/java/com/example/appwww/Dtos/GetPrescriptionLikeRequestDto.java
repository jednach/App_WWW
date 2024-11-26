package com.example.appwww.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetPrescriptionLikeRequestDto {
    private Long patientId;
    private Long doctorId;
    private Long realizedByPharmacyId;
    private Boolean realized;
    private boolean desc = true;
    private int pageNumber = 0;
}
