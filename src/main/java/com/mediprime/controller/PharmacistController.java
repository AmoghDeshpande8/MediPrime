package com.mediprime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mediprime.entity.Pharmacist;
import com.mediprime.service.IPharmacistService;

@Controller
public class PharmacistController {
  
	@Autowired
	private IPharmacistService service;
	
	// to show only login page
	@GetMapping("/pharm")
	public String showLogin() {
		return "Pharmacist_login_page";
	}
	

	@PostMapping("/pharmLogin")
	public String login(@RequestParam String email, @RequestParam String password, Model model) {

		Pharmacist pharmacist = service.login(email, password);

		if (pharmacist != null) {
			return "dashboard"; // success page
		} else {

			// check if user exists
			Pharmacist existing = service.findByEmail(email);

			if (existing == null) {
				model.addAttribute("email", email);
				model.addAttribute("msg", "User not found, please register");
				return "Pharmacist_register_page";
			} else {
				model.addAttribute("error", "Invalid password");
				return "Pharmacist_login_page";
			}
		}
	}
	
	// ✅ Show Register Page
		@GetMapping("/pharmacistRegister")
		public String showRegister() {
			return "Pharmacist_register_page";
		}

		@PostMapping("/pharmacistRegister")
		public String register(@RequestParam String password, 
				@RequestParam String name,
				@RequestParam String email,
				@RequestParam String contact, @RequestParam String license, Model model) {

			if (!contact.matches("\\d{10}")) {
				model.addAttribute("error", "Contact must be exactly 10 digits");

	// 🔥 Keep user entered data

				model.addAttribute("name", name);
				model.addAttribute("email", email);
				model.addAttribute("contact", contact);
				model.addAttribute("license",license);

				return "Pharmacist_register_page";
			}

	// ✅ Username already exists check (BETTER than exception)
			if (service.findByEmail(email) != null) {

				model.addAttribute("error", "Email already exists");

				model.addAttribute("name", name);
				//model.addAttribute("email", email);
				model.addAttribute("contact", contact);
				model.addAttribute("password", password);
				model.addAttribute("license", license);

				return "Pharmacist_register_page";
			}

	// ✅ Save data
			Pharmacist pharmacist = new Pharmacist();

			pharmacist.setPassword(password);
			pharmacist.setName(name);
			pharmacist.setEmail(email);
			pharmacist.setContact(contact);
			pharmacist.setLicense(license);

			service.register(pharmacist);

			model.addAttribute("success", "Registration successful, please login");
			return "Pharmacist_login_page";
		}
}
