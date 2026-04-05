package com.mediprime.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediprime.entity.Appointment;
import com.mediprime.entity.Doctor;
import com.mediprime.entity.Patient;
import com.mediprime.repository.IAppointmentRepository;
import com.mediprime.repository.IDoctorRepository;
import com.mediprime.repository.IPatientRepository;

@Service
public class PatientServiceImpl implements IPatientService {
     @Autowired	
	private IPatientRepository repo;
     
     @Autowired	
     private IAppointmentRepository ApptRepo;
     
     @Autowired
     private IDoctorRepository DocRepo;	
     
     
     @Autowired
     public List<Doctor> getAllDoctors() {
    	    return DocRepo.findAll();
     }

	@Override
	public Patient login(String email, String password) {
		  Patient patient=repo.findByEmail(email);

	        if (patient!= null && patient.getPassword().equals(password)) {
	            return patient; // login success
	        }

	        return null; // login failed
	}

	@Override
	public Patient findByEmail(String email) {
	return repo.findByEmail(email);
	}



	@Override
	public void register(Patient patient) {
		 Patient existing = repo.findByEmail(patient.getEmail());

	        if (existing != null) {
	            throw new RuntimeException("Email already exists");
	        }

	        repo.save(patient);
		
	}

@Override
public void bookAppointment(int patientId, int doctorId, String date,String description,String time) {

    Patient patient = repo.findById(patientId).get();
    Doctor doctor = DocRepo.findById(doctorId).get();

    Appointment appt = new Appointment();
    appt.setPatient(patient);
    appt.setDoctor(doctor);
    appt.setDescription(description);
    appt.setDate(LocalDate.parse(date));
    appt.setTime(time);
    appt.setStatus("PENDING"); // 🔥 important

    ApptRepo.save(appt);
}

@Override
public List<Appointment> getAppointmentsByPatient(int patientId) {


    return ApptRepo.findByPatientId(patientId);
}


}
