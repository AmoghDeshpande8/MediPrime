package com.mediprime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediprime.entity.Doctor;
import com.mediprime.repository.IDoctorRepository;

@Service
public class DoctorService implements IDoctorService {
	
	@Autowired
	private IDoctorRepository repo;

	@Override
	public Doctor login(String email, String password) {
		
		Doctor doctor = repo.findByEmail(email);
		
		if(doctor != null && doctor.getPassword().equals(password))
		{
			return doctor;
		}
		
		return null;
	}

	@Override
	public Doctor findByEmail(String email) {
		// TODO Auto-generated method stub
		return repo.findByEmail(email);
	}

	@Override
	public void register(Doctor doctor) {
		 doctor.setStatus("PENDING"); 
		repo.save(doctor);
		
	}
	
	

}
