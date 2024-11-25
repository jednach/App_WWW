package com.example.appwww.Repositories;

import com.example.appwww.Models.Entities.WorkingHourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface WorkingHourRepository extends JpaRepository<WorkingHourEntity, Long> {
    List<WorkingHourEntity> getWorkingHourEntityByDoctorIdAndDayOfWeek(Long doctorId, DayOfWeek dayOfWeek);
}
