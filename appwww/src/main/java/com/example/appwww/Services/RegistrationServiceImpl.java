package com.example.appwww.Services;

import com.example.appwww.Dtos.CreateRegistrationRequestDto;
import com.example.appwww.Models.Entities.PatientEntity;
import com.example.appwww.Models.Entities.RegistrationEntity;
import com.example.appwww.Repositories.PatientRepository;
import com.example.appwww.Repositories.RegistrationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
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

    public void createRegistration(CreateRegistrationRequestDto createRegistrationRequestDto, Long patientId) {
        PatientEntity patientEntity = patientRepository.findById(patientId).orElseThrow(
                () -> new EntityNotFoundException("Patient not found")
        );
        if (LocalDate.now().equals(createRegistrationRequestDto.getRegistrationDate()) ||
                LocalDate.now().isAfter(createRegistrationRequestDto.getRegistrationDate()))
            throw new RuntimeException("You must register at least one day before the appointment.");

        for (RegistrationEntity registrationEntity : registrationRepository.findByPatientId(patientId)) {
            //TODO: brak mozliwosci rejestracji jezeli mamy oczekujaca rejestracje
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
        //TODO: userId musi nalezec do wlasciciela lub admina
        RegistrationEntity registrationEntity = registrationRepository.findById(registrationId).orElseThrow(
                () -> new EntityNotFoundException("Registration not found")
        );
        registrationRepository.deleteById(registrationId);
    }
}
