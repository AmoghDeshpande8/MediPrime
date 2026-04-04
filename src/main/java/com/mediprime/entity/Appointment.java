package com.mediprime.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate date;
    private LocalTime time; 

    private String description; 

    private String status; 
  

    // Many appointments → one patient
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    // Many appointments → one doctor
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
}
