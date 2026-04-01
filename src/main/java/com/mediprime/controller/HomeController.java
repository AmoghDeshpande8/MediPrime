package com.mediprime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String ShowHome() {
		System.out.println("HomeController.ShowHome()");
		return "index";
	}
}
