package com.mediprime.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mediprime.entity.Admin;
import com.mediprime.entity.Doctor;
import com.mediprime.repository.IAppointmentRepository;
import com.mediprime.repository.IDoctorRepository;
import com.mediprime.service.IAdminService;
import com.mediprime.service.IPatientService;

import jakarta.servlet.http.HttpSession;


@Controller
public class AdminController {

    @Autowired
    private IAdminService service;
    
    @Autowired
    private IDoctorRepository doctorRepo;
    
    @Autowired
    private IAppointmentRepository ApptRepo;
    
    @Autowired
    private IPatientService patservice;
   

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
                        Model model,HttpSession  session) {

        Admin admin = service.login(username, password);

        if (admin != null) {
        	 session.setAttribute("admin", admin);
        	 return "redirect:/dashboard";    // success page
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

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        Admin admin = (Admin) session.getAttribute("admin"); 

        model.addAttribute("admin", admin); // ✅ SEND TO HTML
        
        //to get doc count
        List<Doctor> doctors = patservice.getAllDoctors();
        model.addAttribute("doctorCount", doctors.size());
        

        long totalAppointments = ApptRepo.count();  // ✅ IMPORTANT

        model.addAttribute("admin", admin);
        model.addAttribute("totalAppointments", totalAppointments);
        
        return "admin_dashboard";
    }
    
    @GetMapping("/allAppointments")
    public String allAppointments(Model model) {

        model.addAttribute("appointments", ApptRepo.findAll());

        return "admin_appointments";
    }
    
//    ==
    @GetMapping("/pendingDoctors")
    public String pendingDoctors(Model model) {

        List<Doctor> list = doctorRepo.findAll()
                .stream()
                .filter(d -> "PENDING".equals(d.getStatus()))
                .toList();

        model.addAttribute("doctors", list);

        return "admin_doctors"; // create this page
    }
    @GetMapping("/approveDoctor")
    public String approveDoctor(@RequestParam Integer id) {

        Doctor doc = doctorRepo.findById(id).get();
        doc.setStatus("APPROVED");
        doctorRepo.save(doc);

        return "redirect:/pendingDoctors";
    }
    @GetMapping("/rejectDoctor")
    public String rejectDoctor(@RequestParam Integer id) {

        doctorRepo.deleteById(id);

        return "redirect:/pendingDoctors";
    }
    
    @GetMapping("/admin-profile")
    public String profile(HttpSession session, Model model) {

       Admin admin = (Admin) session.getAttribute("admin");

        model.addAttribute("admin", admin);

        return "admin_profile";
    }
    @GetMapping("/adminlogout")
    public String logout(HttpSession session) {
        session.invalidate();   // destroy session
        return "admin_logout";
    }
    		
}