package com.example.appwww.Controllers;

import com.example.appwww.Dtos.GetMedicineRequestDto;
import com.example.appwww.Dtos.GetMedicineResponseDto;
import com.example.appwww.Models.Enums.MedicineType;
import com.example.appwww.Services.MedicineServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class MedicineController {

    private final MedicineServiceImpl medicineService;

    @Autowired
    public MedicineController(MedicineServiceImpl medicineService) {
        this.medicineService = medicineService;
    }

    @PreAuthorize("hasAnyAuthority('MEDICINE_READ')")
    @GetMapping("/medicines/types")
    public ResponseEntity<Map<String, List<MedicineType>>> getMedicineTypes() {
        Map<String, List<MedicineType>> response = Map.of("medicineTypes",
                new ArrayList<>(medicineService.getAllMedicineTypes()));
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyAuthority('MEDICINE_READ')")
    @PostMapping("/medicines")
    public ResponseEntity<Map<String, List<GetMedicineResponseDto>>> getMedicinesLike(
            @RequestBody GetMedicineRequestDto getMedicineRequestDto) {
        Map<String, List<GetMedicineResponseDto>> response = Map.of("medicines",
                medicineService.getMedicinesLike(getMedicineRequestDto));
        return ResponseEntity.ok(response);
    }
}
