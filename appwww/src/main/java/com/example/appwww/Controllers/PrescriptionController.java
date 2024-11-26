package com.example.appwww.Controllers;

import com.example.appwww.Components.UserResolver;
import com.example.appwww.Dtos.CreatePrescriptionRequestDto;
import com.example.appwww.Dtos.GetPrescriptionDetailsResponseDto;
import com.example.appwww.Dtos.GetPrescriptionLikeRequestDto;
import com.example.appwww.Dtos.GetPrescriptionLikeResponseDto;
import com.example.appwww.Services.PrescriptionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PrescriptionController {

    private final PrescriptionServiceImpl prescriptionService;
    private final UserResolver userResolver;

    @Autowired
    public PrescriptionController(PrescriptionServiceImpl prescriptionService, UserResolver userResolver) {
        this.prescriptionService = prescriptionService;
        this.userResolver = userResolver;
    }

    @PreAuthorize("hasAnyAuthority('PRESCRIPTION_READ')")
    @GetMapping("/prescriptions/{id}")
    public ResponseEntity<GetPrescriptionDetailsResponseDto> getPrescription(@PathVariable("id") Long prescriptionId,
            @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(prescriptionService
                .getPrescription(userResolver.userIdResolver(token), prescriptionId));
    }

    @PreAuthorize("hasAnyAuthority('PRESCRIPTION_READ')")
    @PostMapping("/prescriptions")
    public ResponseEntity<Map<String, List<GetPrescriptionLikeResponseDto>>> getPrescriptionsLike(
            @RequestBody GetPrescriptionLikeRequestDto getPrescriptionLikeRequestDto,
            @RequestHeader(name = "Authorization", required = false) String token) {
        Map<String, List<GetPrescriptionLikeResponseDto>> response = Map.of("prescriptions",
                prescriptionService.getPrescriptionLike(
                        userResolver.userIdResolver(token),getPrescriptionLikeRequestDto));
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyAuthority('PRESCRIPTION_CREATE')")
    @PostMapping("/prescriptions/create")
    public ResponseEntity<?> createPrescription(@RequestBody CreatePrescriptionRequestDto createPrescriptionRequestDto,
                                                @RequestHeader(name = "Authorization", required = false) String token) {
        prescriptionService.createPrescription(userResolver.userIdResolver(token), createPrescriptionRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('PRESCRIPTION_UPDATE')")
    @PutMapping("/prescriptions/{id}")
    public ResponseEntity<?> realizePrescription(
            @PathVariable("id") Long prescriptionId,
            @RequestHeader(name = "Authorization", required = false) String token) {
        prescriptionService.realizePrescription(userResolver.userIdResolver(token), prescriptionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyAuthority('PRESCRIPTION_DELETE')")
    @DeleteMapping("/prescriptions/{id}")
    public ResponseEntity<?> deletePrescription(@PathVariable("id") Long prescriptionId,
                                                @RequestHeader(name = "Authorization", required = false) String token) {
        prescriptionService.deletePrescription(userResolver.userIdResolver(token), prescriptionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
