package com.example.appwww.Models.Entities;

import com.example.appwww.Models.BaseEntityAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "working_hours", schema = "app")
public class WorkingHourEntity extends BaseEntityAudit {
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;
}
