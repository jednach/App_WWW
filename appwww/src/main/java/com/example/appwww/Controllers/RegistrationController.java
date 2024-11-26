package com.example.appwww.Controllers;

import com.example.appwww.Components.UserResolver;
import com.example.appwww.Dtos.CreateRegistrationRequestDto;
import com.example.appwww.Dtos.GetRegistrationForDoctorRequestDto;
import com.example.appwww.Dtos.GetRegistrationForDoctorResponseDto;
import com.example.appwww.Dtos.GetRegistrationOfPatientResponseDto;
import com.example.appwww.Services.RegistrationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class RegistrationController {

    private final RegistrationServiceImpl registrationService;
    private final UserResolver userResolver;

    public RegistrationController(RegistrationServiceImpl registrationService, UserResolver userResolver) {
        this.registrationService = registrationService;
        this.userResolver = userResolver;
    }

    @PreAuthorize("hasAnyAuthority('REGISTRATION_READ')")
    @GetMapping("/registrations/{patientId}")
    public ResponseEntity<Map<String, List<GetRegistrationOfPatientResponseDto>>> getRegistrationForDoctor(
            @PathVariable("patientId") Long patientId,
            @RequestHeader(name = "Authorization", required = false) String token) {
        Map<String, List<GetRegistrationOfPatientResponseDto>> response = Map.of("patientRegistrations",
                registrationService.getRegistrationsOfPatient(patientId, userResolver.userIdResolver(token)));
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyAuthority('REGISTRATIONS_READ')")
    @PostMapping("/registrations/{doctorId}")
    public ResponseEntity<Map<String, List<GetRegistrationForDoctorResponseDto>>> getRegistrationForDoctorAt(
            @PathVariable("doctorId") Long doctorId,
            @RequestBody GetRegistrationForDoctorRequestDto getRegistrationForDoctorRequestDto) {
        Map<String, List<GetRegistrationForDoctorResponseDto>> response = Map.of("registrations",
                registrationService.getRegistrationForDoctorAt(doctorId, getRegistrationForDoctorRequestDto));
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyAuthority('REGISTRATION_CREATE')")
    @PostMapping("/registrations/create")
    public ResponseEntity<?> createRegistration(@RequestBody CreateRegistrationRequestDto registrationRequestDto,
                                                @RequestHeader(name = "Authorization", required = false) String token) {
        registrationService.createRegistration(registrationRequestDto, userResolver.userIdResolver(token));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('REGISTRATION_DELETE')")
    @DeleteMapping("/registration/{id}")
    public ResponseEntity<?> deleteRegistration(@PathVariable("id") Long id,
                                                @RequestHeader(name = "Authorization", required = false) String token) {
        registrationService.deleteRegistration(id, userResolver.userIdResolver(token));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
