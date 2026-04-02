package com.mediprime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mediprime.entity.Patient;
import com.mediprime.service.IPatientService;

@Controller
public class PatientController {

	@Autowired
	private IPatientService service;

	// to show only login page
	@GetMapping("/patient")
	public String showLogin() {
		return "Patient_login_page";
	}

	@PostMapping("/patientLogin")
	public String login(@RequestParam String email, @RequestParam String password, Model model) {

		Patient patient = service.login(email, password);

		if (patient != null) {
			return "dashboard"; // success page
		} else {

			// check if user exists
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
	
	  // ✅ Show Register Page
    @GetMapping("/patientRegister")
    public String showRegister() {
        return "Patient_register_page";
    }
    
    @PostMapping("/patientRegister")
    public String register(
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String contact,
           @RequestParam int age,
            Model model) {


if (!contact.matches("\\d{10}")) {
model.addAttribute("error", "Contact must be exactly 10 digits");


// 🔥 Keep user entered data

model.addAttribute("name", name);
model.addAttribute("email", email);
model.addAttribute("contact", contact);
model.addAttribute("age", age);

return "Patient_register_page";
}

// ✅ Username already exists check (BETTER than exception)
if (service.findByEmail(email) != null) {

model.addAttribute("error", "Email already exists");


model.addAttribute("name", name);
//model.addAttribute("email", email);
model.addAttribute("contact", contact);
model.addAttribute("password", password);
model.addAttribute("age", age);

return "Patient_register_page";
}

// ✅ Save data
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

}
