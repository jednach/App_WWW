package com.example.appwww.Controllers;

import com.example.appwww.Components.UserResolver;
import com.example.appwww.Dtos.GetPatientBookResponseDto;
import com.example.appwww.Dtos.CreatePatientBookRequestDto;
import com.example.appwww.Services.PatientBookServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class PatientBookController {

    private final PatientBookServiceImpl patientBookService;
    private final UserResolver userResolver;

    public PatientBookController(PatientBookServiceImpl patientBookService, UserResolver userResolver) {
        this.patientBookService = patientBookService;
        this.userResolver = userResolver;
    }

    @PreAuthorize("hasAnyAuthority('PATIENTBOOK_READ')")
    @GetMapping("/patientBooks/{patientId}")
    public ResponseEntity<GetPatientBookResponseDto> getPatientBook(
            @PathVariable Long patientId, @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(patientBookService.getPatientBook(userResolver.userIdResolver(token), patientId));
    }

    @PreAuthorize("hasAnyAuthority('PATIENTBOOK_CREATE')")
    @PostMapping("/patientBooks/{patientId}")
    public ResponseEntity<?> createPatientBook(@PathVariable("patientId") Long patientId,
                                               @RequestBody CreatePatientBookRequestDto createPatientBookRequestDto) {
        patientBookService.createPatientBook(patientId, createPatientBookRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
