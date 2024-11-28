package com.example.appwww.Controllers;

import com.example.appwww.Dtos.CreateDoctorRequestDto;
import com.example.appwww.Dtos.CreatePharmacyRequestDto;
import com.example.appwww.Services.AuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class AdminController {

    private final AuthenticationServiceImpl authenticationService;

    @Autowired
    public AdminController(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR_CREATE')")
    @PostMapping("/admin/create/doctor")
    public ResponseEntity<?> createDoctor(@RequestBody CreateDoctorRequestDto createDoctorRequestDto) {
        authenticationService.createDoctor(createDoctorRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('PHARMACY_CREATE')")
    @PostMapping("/admin/create/pharmacy")
    public ResponseEntity<?> createPharmacy(@RequestBody CreatePharmacyRequestDto createPharmacyRequestDto) {
        authenticationService.createPharmacy(createPharmacyRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
