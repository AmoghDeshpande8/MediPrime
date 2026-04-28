package com.mediprime.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mediprime.entity.Appointment;
import com.mediprime.entity.Doctor;
import com.mediprime.entity.Patient;
import com.mediprime.service.IPatientService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PatientController {

    @Autowired
    private IPatientService service;

    // ================= LOGIN =================

    
    //to open login page
    @GetMapping("/patient")
    public String showLogin() {
        return "Patient_login_page";
    }

    //if success then dashboard else register
    @PostMapping("/patientLogin")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {

        Patient patient = service.login(email, password);

        if (patient != null) {

       
            session.setAttribute("loggedPatient", patient);
             
            
            //if login open patient dashboard
            return "redirect:/patientDashboard";

        } else {

            Patient existing = service.findByEmail(email);

            if (existing == null) {
                model.addAttribute("email", email);
                model.addAttribute("msg", "User not found, please register");
                return "Patient_register_page";
            } else {
                model.addAttribute("error", "Invalid password");
                return "Patient_login_page";
            }
        }
    }

    // ================= DASHBOARD =================

    @GetMapping("/patientDashboard")
    public String patientDashboard(Model model, HttpSession session) {

        Patient patient = (Patient) session.getAttribute("loggedPatient");

        if (patient == null) {
            return "redirect:/patient";
        }

       
        model.addAttribute("patient", patient);

        //  Doctor count
        List<Doctor> doctors = service.getAllDoctors();
        model.addAttribute("doctorCount", doctors.size());

        // Appointment count
        List<Appointment> appointments =service.getAppointmentsByPatient(patient.getId());
        model.addAttribute("appointmentCount", appointments.size());

        return "patient_dashboard";
    }

    // ================= REGISTER =================

    @GetMapping("/patientRegister")
    public String showRegister() {
        return "Patient_register_page";
    }

    @PostMapping("/patientRegister")
    public String register(@RequestParam String password,
                           @RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String contact,
                           @RequestParam int age,
                           Model model) {

        if (!contact.matches("\\d{10}")) {
            model.addAttribute("error", "Contact must be exactly 10 digits");

            model.addAttribute("name", name);
            model.addAttribute("email", email);
            model.addAttribute("contact", contact);
            model.addAttribute("age", age);

            return "Patient_register_page";
        }

        if (service.findByEmail(email) != null) {

            model.addAttribute("error", "Email already exists");

            model.addAttribute("name", name);
            model.addAttribute("contact", contact);
            model.addAttribute("password", password);
            model.addAttribute("age", age);

            return "Patient_register_page";
        }

        Patient patient = new Patient();
        patient.setPassword(password);
        patient.setName(name);
        patient.setEmail(email);
        patient.setContact(contact);
        patient.setAge(age);

        service.register(patient);

        model.addAttribute("success", "Registration successful, please login");
        return "Patient_login_page";
    }

    // ================= TAKE APPOINTMENT =================

    //this will open appointment taking page
    @GetMapping("/takeAppointment")
    public String showAppointmentPage(Model model) {

        model.addAttribute("doctors", service.getAllDoctors());

        return "take_appointment";
    }

    // ================= BOOK APPOINTMENT =================

    @PostMapping("/bookAppointment")
    public String bookAppointment(@RequestParam int doctorId,
                                  @RequestParam String date,
                                  @RequestParam String description,
                                  @RequestParam String time,
                                  HttpSession session,
                                 RedirectAttributes r ) {

        Patient patient = (Patient) session.getAttribute("loggedPatient");

        if (patient == null) {
            return "redirect:/patient";
        }

        service.bookAppointment(patient.getId(), doctorId, date, description, time);

    
        r.addFlashAttribute("success", "Appointment booked successfully");

        
        return "redirect:/patientDashboard";
    }

    @GetMapping("/patient-profile")
    public String profile(HttpSession session, Model model) {

        Patient patient = (Patient) session.getAttribute("loggedPatient");

        model.addAttribute("patient", patient);

        return "patient_profile";
    }
    @GetMapping("/patlogout")
    public String logout(HttpSession session) {
        session.invalidate();   // destroy session
        return "patient_logout";
    }

}