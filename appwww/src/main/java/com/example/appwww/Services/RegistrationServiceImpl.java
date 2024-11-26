package com.example.appwww.Services;

import com.example.appwww.Dtos.CreateRegistrationRequestDto;
import com.example.appwww.Dtos.GetRegistrationForDoctorRequestDto;
import com.example.appwww.Dtos.GetRegistrationForDoctorResponseDto;
import com.example.appwww.Dtos.GetRegistrationOfPatientResponseDto;
import com.example.appwww.Models.Entities.DoctorEntity;
import com.example.appwww.Models.Entities.PatientEntity;
import com.example.appwww.Models.Entities.RegistrationEntity;
import com.example.appwww.Repositories.PatientRepository;
import com.example.appwww.Repositories.RegistrationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationServiceImpl {

    private final RegistrationRepository registrationRepository;
    private final PatientRepository patientRepository;
    private final DoctorServiceImpl doctorServiceImpl;

    @Autowired
    public RegistrationServiceImpl(RegistrationRepository registrationRepository, PatientRepository patientRepository, DoctorServiceImpl doctorServiceImpl) {
        this.registrationRepository = registrationRepository;
        this.patientRepository = patientRepository;
        this.doctorServiceImpl = doctorServiceImpl;
    }

    public List<GetRegistrationOfPatientResponseDto> getRegistrationsOfPatient(Long patientId, Long userId){
        PatientEntity patient = patientRepository.findById(patientId).orElseThrow(
                () -> new EntityNotFoundException("Patient not found")
        );
        if(!patient.getId().equals(userId) && !userId.equals(-1L))
            throw new RuntimeException("You are not supposed to see this");
        List<RegistrationEntity> registrations = registrationRepository.findByPatientId(patientId);
        List<GetRegistrationOfPatientResponseDto> response = new ArrayList<>();
        for (RegistrationEntity registration : registrations) {
            response.add(new GetRegistrationOfPatientResponseDto(
                    registration.getId(),
                    registration.getDoctor().getId(),
                    registration.getVisitDate(),
                    registration.getVisitTime()
            ));
        }
        response.sort((a,b) -> b.getDate().compareTo(a.getDate()));
        return response;
    }

    public List<GetRegistrationForDoctorResponseDto> getRegistrationForDoctorAt(
            Long doctorId, GetRegistrationForDoctorRequestDto getRegistrationForDoctorRequestDto) {
        DoctorEntity doctorEntity = doctorServiceImpl.getDoctorById(doctorId);
        List<RegistrationEntity> registrations = registrationRepository
                .findByDoctorIdAndVisitDateOrderByVisitTimeAsc(doctorId, getRegistrationForDoctorRequestDto.getDate());
        List<GetRegistrationForDoctorResponseDto> response = new ArrayList<>();
        for(RegistrationEntity registration : registrations) {
            response.add(new GetRegistrationForDoctorResponseDto(
                    registration.getId(),
                    registration.getPatient().getId(),
                    registration.getVisitTime()
            ));
        }
        return response;
    }

    public void createRegistration(CreateRegistrationRequestDto createRegistrationRequestDto, Long patientId) {
        PatientEntity patientEntity = patientRepository.findById(patientId).orElseThrow(
                () -> new EntityNotFoundException("Patient not found")
        );
        if (createRegistrationRequestDto.getRegistrationTime().isBefore(LocalTime.of(8, 0, 0)) ||
                createRegistrationRequestDto.getRegistrationTime().isAfter(LocalTime.of(15, 0, 0)) ||
                createRegistrationRequestDto.getRegistrationTime().getMinute() % 15 != 0)
            throw new RuntimeException("This is not valid registration time");

        if (LocalDate.now().equals(createRegistrationRequestDto.getRegistrationDate()) ||
                LocalDate.now().isAfter(createRegistrationRequestDto.getRegistrationDate()))
            throw new RuntimeException("You must register at least one day before the appointment.");

        for (RegistrationEntity registrationEntity : registrationRepository.findByPatientId(patientId)) {
            LocalDate tempDateTime = registrationEntity.getVisitDate();
            if (tempDateTime.equals(createRegistrationRequestDto.getRegistrationDate()))
                throw new RuntimeException("You are already registered for an appointment at this day.");
        }

        List<LocalTime[]> openHours = doctorServiceImpl.getOpenHoursOfDate(createRegistrationRequestDto.getDoctorId(),
                createRegistrationRequestDto.getRegistrationDate());
        boolean flag = false;
        LocalTime startTime = createRegistrationRequestDto.getRegistrationTime();
        for (LocalTime[] openHour : openHours) {
            if (openHour[0].equals(startTime) || (openHour[0].isBefore(startTime) && openHour[1].isAfter(startTime))) {
                flag = true;
                break;
            }
        }
        if (!flag) throw new RuntimeException("You cannot register for this hours!");
        RegistrationEntity registrationEntity = new RegistrationEntity();
        registrationEntity.setPatient(patientEntity);
        registrationEntity.setDoctor(doctorServiceImpl.getDoctorById(createRegistrationRequestDto.getDoctorId()));
        registrationEntity.setVisitDate(createRegistrationRequestDto.getRegistrationDate());
        registrationEntity.setVisitTime(createRegistrationRequestDto.getRegistrationTime());
        registrationRepository.save(registrationEntity);
    }

    public void deleteRegistration(Long registrationId, Long userId) {
        RegistrationEntity registrationEntity = registrationRepository.findById(registrationId).orElseThrow(
                () -> new EntityNotFoundException("Registration not found")
        );
        if (!registrationEntity.getPatient().getId().equals(userId) && !userId.equals(-1L))
            throw new RuntimeException("You cannot delete an appointment that is not yours.");
        registrationRepository.deleteById(registrationId);
    }
}
