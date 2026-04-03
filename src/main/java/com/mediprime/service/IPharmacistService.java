package com.mediprime.service;

import com.mediprime.entity.Pharmacist;

public interface IPharmacistService {
	  Pharmacist login(String email, String password);

	    Pharmacist findByEmail(String email);

	    void register(Pharmacist pharmacist );
}
