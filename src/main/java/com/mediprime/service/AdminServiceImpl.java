package com.mediprime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediprime.entity.Admin;
import com.mediprime.repository.IAdminRepository;

@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private IAdminRepository repo;

    @Override
    public Admin login(String email, String password) {

        Admin admin = repo.findByUsername(email);

        if (admin != null && admin.getPassword().equals(password)) {
            return admin; // login success
        }

        return null; // login failed
    }

    @Override
    public Admin findByUsername(String email) {
        return repo.findByUsername(email);
    }

    @Override
    public void register(Admin admin) {
        repo.save(admin);
    }
}