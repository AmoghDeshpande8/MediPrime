package com.mediprime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mediprime.entity.Patient;
@Repository
public interface IPatientRepository extends JpaRepository<Patient,Integer>{
	 Patient findByEmail(String email);

}
