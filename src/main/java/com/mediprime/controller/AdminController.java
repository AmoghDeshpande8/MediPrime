package com.mediprime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mediprime.entity.Admin;
import com.mediprime.repository.IAppointmentRepository;
import com.mediprime.service.IAdminService;


@Controller
public class AdminController {

    @Autowired
    private IAdminService service;
    
    @Autowired
    private IAppointmentRepository ApptRepo;
    
   

    // ✅ Default/Home page
    @GetMapping("/")
    public String home() {
        return "index";   // index.html (Thymeleaf)
    }

    // ✅ Show Login Page
    @GetMapping("/admin")
    public String showLogin() {
        return "Admin_login_page";
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
                return "Admin_register_page";
            } else {
                model.addAttribute("error", "Invalid password");
                return "Admin_login_page";
            }
        }
    }

    // ✅ Show Register Page
    @GetMapping("/register")
    public String showRegister() {
        return "Admin_register_page";
    }

    // ✅ Register Logic
    @PostMapping("/register")
    public String register(@ModelAttribute("admin") Admin admin,
                           org.springframework.validation.BindingResult result,
                           Model model) {

        // 🔴 Validation errors (like contact not 10 digits)
        if (result.hasErrors()) {
            return "Admin_register_page";
        }

        // 🔴 Check username exists
        if (service.findByUsername(admin.getUsername()) != null) {
            model.addAttribute("error", "Username already exists");

            return "Admin_register_page";
        }

        service.register(admin);

        model.addAttribute("success", "Registration successful, please login");
        return "Admin_login_page";
    }

    // ✅ Dashboard Page
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
    
    @GetMapping("/allAppointments")
    public String allAppointments(Model model) {

        model.addAttribute("appointments", ApptRepo.findAll());

        return "admin_dashboard";
    }
}