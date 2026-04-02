package com.mediprime.service;

import com.mediprime.entity.Admin;

public interface IAdminService {

    Admin login(String username, String password);

    Admin findByUsername(String username);

    void register(Admin admin);
}