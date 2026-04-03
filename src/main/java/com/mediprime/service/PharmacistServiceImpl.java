package com.mediprime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediprime.entity.Pharmacist;
import com.mediprime.repository.IPharmacistRepository;

@Service
public class PharmacistServiceImpl  implements IPharmacistService{

	@Autowired
	private IPharmacistRepository repo;
	
	@Override
	public Pharmacist login(String email, String password) {
		Pharmacist pharmacist=repo.findByEmail(email);

        if (pharmacist!= null && pharmacist.getPassword().equals(password)) {
            return pharmacist; // login success
        }

        return null; // login failed
	}

	@Override
	public Pharmacist findByEmail(String email) {
		return repo.findByEmail(email);
	}

	@Override
	public void register(Pharmacist pharmacist) {
		 Pharmacist existing = repo.findByEmail(pharmacist.getEmail());

	        if (existing != null) {
	            throw new RuntimeException("Email already exists");
	        }

	        repo.save(pharmacist);
		
	}

}
