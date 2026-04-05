package com.mediprime.service;

import java.util.List;

import com.mediprime.entity.Appointment;
import com.mediprime.entity.Doctor;
import com.mediprime.entity.Patient;

public interface IPatientService {
	  Patient login(String email, String password);

	    Patient findByEmail(String email);

	    void register(Patient patient);
	    
//	    ===
	    List<Doctor> getAllDoctors();
	    void bookAppointment(int patientId, int doctorId, String date,String description,String time);
	    
	    List<Appointment> getAppointmentsByPatient(int patientId);
}
