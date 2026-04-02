package com.mediprime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mediprime.entity.Doctor;
import com.mediprime.service.IDoctorService;

@Controller
@RequestMapping("/doctor-api")
public class DoctorController {
	
	@Autowired
	private IDoctorService service;
	
	@GetMapping("/doctor-login")
    public String showLogin() {
        return "doctor_login";
    }
	
	 @PostMapping("/login")
	    public String login(@RequestParam String email,
	                        @RequestParam String password,
	                        Model model) {

	        Doctor doctor = service.login(email, password);

	        if (doctor != null) {
	            return "doctor_dashboard";   // success page
	        } else {

	            // check if user exists
	            Doctor existing = service.findByEmail(email);

	            if (existing == null) {
	                model.addAttribute("email", email);
	                model.addAttribute("msg", "User not found, please register");
	                return "doctor_register";
	            } else {
	                model.addAttribute("error", "Invalid password");
	                return "doctor_login";
	            }
	        }
	    }
	 
	 @GetMapping("/doctor-register")
	    public String showRegister() {
	        return "doctor_register";
	    }

	    // ✅ Register Logic
	    @PostMapping("/register")
	    public String register(@ModelAttribute("admin") Doctor doctor,
	                           org.springframework.validation.BindingResult result,
	                           Model model) {

	        // 🔴 Validation errors (like contact not 10 digits)
	        if (result.hasErrors()) {
	            return "doctor_register";
	        }

	        // 🔴 Check username exists
	        if (service.findByEmail(doctor.getEmail()) != null) {
	            model.addAttribute("error", "Email already exists");
	            return "doctor_register";
	        }

	        service.register(doctor);

	        model.addAttribute("success", "Registration successful, please login");
	        return "doctor_login";
	    }
	    // ✅ Dashboard Page
	    @GetMapping("/doctor")
	    public String dashboard() {
	        return "doctor_dashboard";
	    }
}
