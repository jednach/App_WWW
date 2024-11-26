package com.example.appwww.Controllers;

import com.example.appwww.Dtos.GetPatientRequestDto;
import com.example.appwww.Dtos.GetPatientResponseDto;
import com.example.appwww.Services.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class PatientController {

    private final PatientServiceImpl patientService;

    @Autowired
    public PatientController(PatientServiceImpl patientService) {
        this.patientService = patientService;
    }

    @PreAuthorize("hasAnyAuthority('PATIENT_READ')")
    @PostMapping("/patients")
    public ResponseEntity<Map<String, List<GetPatientResponseDto>>> getPatients(
            @RequestBody GetPatientRequestDto getPatientRequestDto) {
        Map<String, List<GetPatientResponseDto>> response = Map.of("patients",
                patientService.getPatients(getPatientRequestDto));
        return ResponseEntity.ok(response);
    }

}
