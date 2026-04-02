package com.mediprime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mediprime.entity.Admin;
import com.mediprime.service.IAdminService;

@Controller
public class AdminController {

    @Autowired
    private IAdminService service;

    // ✅ Default/Home page
    @GetMapping("/")
    public String home() {
        return "index";   // index.html (Thymeleaf)
    }

    // ✅ Show Login Page
    @GetMapping("/admin")
    public String showLogin() {
        return "login_page";
    }

    // ✅ Login Logic
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {

        Admin admin = service.login(username, password);

        if (admin != null) {
            return "dashboard";   // success page
        } else {

            // check if user exists
            Admin existing = service.findByUsername(username);

            if (existing == null) {
                model.addAttribute("username", username);
                model.addAttribute("msg", "User not found, please register");
                return "register_page";
            } else {
                model.addAttribute("error", "Invalid password");
                return "login_page";
            }
        }
    }

    // ✅ Show Register Page
    @GetMapping("/register")
    public String showRegister() {
        return "register_page";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String contact,
                           @RequestParam String role,
                           Model model) {

      
        if (!contact.matches("\\d{10}")) {
            model.addAttribute("error", "Contact must be exactly 10 digits");

           
            model.addAttribute("username", username);
            model.addAttribute("name", name);
            model.addAttribute("email", email);
            model.addAttribute("contact", contact);
            model.addAttribute("role", role);

            return "register_page";
        }

        // ✅ Username already exists check (BETTER than exception)
        if (service.findByUsername(username) != null) {

            model.addAttribute("error", "Username already exists");

            model.addAttribute("username", username);
            model.addAttribute("name", name);
            model.addAttribute("email", email);
            model.addAttribute("contact", contact);
            model.addAttribute("role", role);

            return "register_page";
        }

        // ✅ Save data
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setName(name);
        admin.setEmail(email);
        admin.setContact(contact);
        admin.setRole(role);

        service.register(admin);

        model.addAttribute("success", "Registration successful, please login");
        return "login_page";
    }
    
    // ✅ Dashboard Page
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}