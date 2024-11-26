package com.example.appwww.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPrescriptionDetailsResponseDto {
    private Long prescriptionId;
    private Long patientId;
    private Long doctorId;
    private boolean realized;
    private Long realizedByPharmacyId;
    private List<GetMedicineResponseDto> medicines;
}
