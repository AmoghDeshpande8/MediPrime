package com.mediprime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mediprime.entity.Pharmacist;

@Repository
public interface IPharmacistRepository extends JpaRepository<Pharmacist,Integer> {
	 Pharmacist findByEmail(String email);
}
