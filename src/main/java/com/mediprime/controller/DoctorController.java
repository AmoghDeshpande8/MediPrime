package com.mediprime.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mediprime.entity.Doctor;
import com.mediprime.entity.Patient;
import com.mediprime.service.IDoctorService;

import jakarta.servlet.http.HttpSession;

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
	                    Model model, HttpSession session) {

	    Doctor doctor = service.login(email, password);

	    if (doctor != null) {

	        if ("APPROVED".equals(doctor.getStatus())) {

	            session.setAttribute("doctor", doctor);
	            model.addAttribute("doctor", doctor);
	            return "doctor_dashboard";

	        } else {
	            model.addAttribute("error", "Account not approved yet");
	            return "doctor_login";
	        }

	    } else {

	        Doctor existing = service.findByEmail(email);

	        if (existing == null) {
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
	 public String register(@ModelAttribute("doctor") Doctor doctor,
	                        org.springframework.validation.BindingResult result,
	                        Model model) {

	     if (result.hasErrors()) {
	         return "doctor_register";
	     }

	     if (service.findByEmail(doctor.getEmail()) != null) {
	         model.addAttribute("error", "Email already exists");
	         return "doctor_register";
	     }

	     // ✅ Important line
	     doctor.setStatus("PENDING");

	     service.register(doctor);

	     model.addAttribute("success", "Registration successful, wait for admin approval");
	     return "doctor_login";
	 }
	 
	 
	 
	    // ✅ Dashboard Page
	    @GetMapping("/doctor")
	    public String dashboard(HttpSession session, Model model) {
	    	Doctor doctor = (Doctor) session.getAttribute("doctor");
	    	model.addAttribute("doctor", doctor);
	        return "doctor_dashboard";
	    }
	    
	    @GetMapping("/appointments")
	    public String appointments(Model model, HttpSession session) {

	        Doctor doctor = (Doctor) session.getAttribute("doctor");

	        if (doctor == null) {
	            return "redirect:/doctor-api/doctor-login";
	        }

	        model.addAttribute("appointments", doctor.getAppointments());

	        return "doctor_appointments";
	    }
	    
	    @GetMapping("/patients")
	    public String patients(Model model, HttpSession session) {

	        Doctor doctor = (Doctor) session.getAttribute("doctor");

	        if (doctor == null) {
	            return "redirect:/doctor-api/doctor-login";
	        }

	        // get patients from appointments
	        List<Patient> patients = doctor.getAppointments()
	                .stream()
	                .map(a -> a.getPatient())
	                .distinct()
	                .toList();

	        model.addAttribute("patients", patients);

	        return "doctor_patients";
	    }
	    
	    @GetMapping("/profile")
	    public String profile(HttpSession session, Model model) {

	        Doctor doctor = (Doctor) session.getAttribute("doctor");

	        model.addAttribute("doctor", doctor);

	        return "doctor_profile";
	    }
	    
	    @GetMapping("/logout")
	    public String logout(HttpSession session) {
	        session.invalidate();   // destroy session
	        return "logout";
	    }
}
