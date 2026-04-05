package com.mediprime.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediprime.entity.Appointment;

public interface IAppointmentRepository  extends JpaRepository<Appointment, Integer> {

	    List<Appointment> findByDoctorId(int doctorId);

	    List<Appointment> findAll(); // admin use
	    
	    // ✅ fetch appointments by patient id
	    List<Appointment> findByPatientId(int patientId);
	}

