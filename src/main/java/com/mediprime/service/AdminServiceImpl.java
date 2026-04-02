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
    public Admin login(String username, String password) {

        Admin admin = repo.findByUsername(username);

        if (admin != null && admin.getPassword().equals(password)) {
            return admin; // login success
        }

        return null; // login failed
    }

    @Override
    public Admin findByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public void register(Admin admin) {

        Admin existing = repo.findByUsername(admin.getUsername());

        if (existing != null) {
            throw new RuntimeException("Username already exists");
        }

        repo.save(admin);
    }
}