package com.mediprime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediprime.entity.Patient;
import com.mediprime.repository.IPatientRepository;
@Service
public class PatientServiceImpl implements IPatientService {
     @Autowired	
	private IPatientRepository repo;
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
	            throw new RuntimeException("Username already exists");
	        }

	        repo.save(patient);
		
	}

}
