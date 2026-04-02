package com.mediprime.service;

import com.mediprime.entity.Patient;

public interface IPatientService {
	  Patient login(String email, String password);

	    Patient findByEmail(String email);

	    void register(Patient patient);
}
