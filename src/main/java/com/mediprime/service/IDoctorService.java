package com.mediprime.service;

import com.mediprime.entity.Doctor;

public interface IDoctorService {
	Doctor login(String email, String password);

    Doctor findByEmail(String email);
    
    void register(Doctor doctor);
}
