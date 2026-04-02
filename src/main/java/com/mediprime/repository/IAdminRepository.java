package com.mediprime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mediprime.entity.Admin;

@Repository
public interface IAdminRepository extends JpaRepository<Admin,Integer> {

    // Login check
    //Admin findByUsernameAndPassword(String username, String password);

    // Check if username exists
    Admin findByUsername(String username);
    
    
}