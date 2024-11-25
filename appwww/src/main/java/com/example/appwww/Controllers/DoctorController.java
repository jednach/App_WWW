package com.example.appwww.Controllers;

import com.example.appwww.Dtos.GetDoctorOpenHoursRequestDto;
import com.example.appwww.Dtos.GetDoctorResponseDto;
import com.example.appwww.Dtos.GetDoctorWorkingHoursRequestDto;
import com.example.appwww.Services.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class DoctorController {

    private final DoctorServiceImpl doctorService;

    @Autowired
    public DoctorController(DoctorServiceImpl doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/doctors")
    public ResponseEntity<Map<String, List<GetDoctorResponseDto>>> getDoctors() {
        Map<String, List<GetDoctorResponseDto>> response = Map.of("doctors", doctorService.getAllDoctors());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/doctors/{doctorId}/working-hours")
    public ResponseEntity<Map<String, List<LocalTime[]>>> getDoctorWorkingHours(
            @PathVariable("doctorId") Long doctorId,
            @RequestBody GetDoctorWorkingHoursRequestDto workingHoursRequestDto) {
        Map<String, List<LocalTime[]>> response = Map.of(
                "hours", doctorService.getWorkingHoursOfDay(doctorId, workingHoursRequestDto.getDayOfWeek()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/doctors/{doctorId}/open-hours")
    public ResponseEntity<?> getDoctorOpenHours(@PathVariable("doctorId") Long doctorId,
                                                @RequestBody GetDoctorOpenHoursRequestDto openHoursRequestDto) {
        return ResponseEntity.ok(doctorService.getOpenHoursOfDate(doctorId, openHoursRequestDto.getDate()));
    }
}
