package com.example.appwww.Services;

import com.example.appwww.Dtos.GetDoctorResponseDto;
import com.example.appwww.Models.Entities.DoctorEntity;
import com.example.appwww.Models.Entities.RegistrationEntity;
import com.example.appwww.Models.Entities.WorkingHourEntity;
import com.example.appwww.Repositories.DoctorRepository;
import com.example.appwww.Repositories.RegistrationRepository;
import com.example.appwww.Repositories.WorkingHourRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class DoctorServiceImpl {

    private final DoctorRepository doctorRepository;
    private final WorkingHourRepository workingHourRepository;
    private final RegistrationRepository registrationRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, WorkingHourRepository workingHourRepository, RegistrationRepository registrationRepository) {
        this.doctorRepository = doctorRepository;
        this.workingHourRepository = workingHourRepository;
        this.registrationRepository = registrationRepository;
    }

    public DoctorEntity getDoctorById(Long doctorId) {
        return doctorRepository.findById(doctorId).orElseThrow(
                () -> new EntityNotFoundException("Doctor not found")
        );
    }

    public List<GetDoctorResponseDto> getAllDoctors() {
        List<GetDoctorResponseDto> response = new ArrayList<>();
        for (DoctorEntity doctor : doctorRepository.findAll()) {
            response.add(new GetDoctorResponseDto(
                    doctor.getId(),
                    doctor.getFirstName(),
                    doctor.getLastName())
            );
        }
        return response;
    }

    public List<LocalTime[]> getWorkingHoursOfDay(Long doctorId, DayOfWeek dayOfWeek) {
        DoctorEntity doctor = getDoctorById(doctorId);
        List<WorkingHourEntity> workingHours = workingHourRepository
                .getWorkingHourEntityByDoctorIdAndDayOfWeek(doctor.getId(), dayOfWeek);
        List<LocalTime[]> response = new ArrayList<>();
        for (WorkingHourEntity workingHour : workingHours)
            response.add(new LocalTime[]{workingHour.getStartTime(), workingHour.getEndTime()});
        response.sort((a, b) -> a[0].compareTo(b[0]));
        return response;
    }

    public List<LocalTime[]> getOpenHoursOfDate(Long doctorId, LocalDate date) {
        List<LocalTime[]> workingHours = getWorkingHoursOfDay(doctorId, date.getDayOfWeek());
        if (workingHours.isEmpty()) return new ArrayList<>();
        List<LocalTime[]> closedHours = new ArrayList<>();
        if (workingHours.get(0)[0].isAfter(LocalTime.of(8, 0, 0))) closedHours.add(new LocalTime[]{
                LocalTime.of(8, 0, 0), workingHours.get(0)[0]});
        int i = 0;
        for (; i < workingHours.size() - 1; i++)
            closedHours.add(new LocalTime[]{workingHours.get(i)[1], workingHours.get(i + 1)[0]});
        if(workingHours.get(i)[1].isBefore(LocalTime.of(15, 0, 0))) closedHours.add(new LocalTime[]{
                workingHours.get(i)[1], LocalTime.of(15, 0, 0)});
        List<RegistrationEntity> registrations = registrationRepository
                .findByDoctorIdAndVisitDateOrderByVisitTimeAsc(doctorId, date);
        for(RegistrationEntity registration : registrations) {
            LocalTime startTime = registration.getVisitTime();
            LocalTime endTime = startTime.plusMinutes(15);
            closedHours.add(new LocalTime[]{startTime, endTime});
        }
        List<LocalTime[]> response = new ArrayList<>();
        closedHours.sort((a, b) -> a[0].compareTo(b[0]));
        LocalTime startTime = LocalTime.of(8, 0, 0);
        for(LocalTime[] closedHour : closedHours) {
            if(startTime.isBefore(closedHour[0])) response.add(new LocalTime[]{startTime, closedHour[0]});
            startTime = closedHour[1];
        }
        if(startTime.isBefore(workingHours.get(workingHours.size() - 1)[1]))
            response.add(new LocalTime[]{startTime, workingHours.get(workingHours.size() - 1)[1]});
        return response;
    }
}
