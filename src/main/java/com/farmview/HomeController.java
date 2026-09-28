package com.farmview;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/")
	public String home(Model model) {
		
		LocalDate today = LocalDate.now();
		model.addAttribute("date", today);
		
		return "index";
	}
	@GetMapping("/weather")
	public String weather() {
		return "weather";
	}
	@GetMapping("/records")
	public String records() {
		return "records";
	}
	@GetMapping("/records/new")
	public String recordForm() {
		return "record-form";
	}
}
