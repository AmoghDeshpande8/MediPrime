package com.mediprime.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediprime.entity.Admin;
import com.mediprime.entity.Doctor;

public interface IDoctorRepository extends JpaRepository<Doctor, Integer> {
	
	Doctor findByEmail(String email);
}
