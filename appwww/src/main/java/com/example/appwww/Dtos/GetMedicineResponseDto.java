package com.example.appwww.Dtos;

import com.example.appwww.Models.Enums.MedicineType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetMedicineResponseDto {
    private Long medicineId;
    private String medicineName;
    private MedicineType medicineType;
    private String medicineDescription;
}
