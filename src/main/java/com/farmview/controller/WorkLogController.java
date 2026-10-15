package com.farmview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.farmview.entity.WorkLog;
import com.farmview.service.WorkLogService;

@Controller
public class WorkLogController {
	
	private final WorkLogService service;
	
	public WorkLogController(WorkLogService service) {
		this.service = service;
	}
	
	@GetMapping("/records")
	public String list(Model model) {
		model.addAttribute("records", service.findAll());
		return "records";
	}
	@GetMapping("/records/{id}")
	public String detail(@PathVariable Long id, Model model) {
		WorkLog workLog = service.findById(id);
		model.addAttribute("record", workLog);
		return "record-detail";
	}
	@GetMapping("/records/{id}/edit")
	public String edit(@PathVariable Long id, Model model) {
		WorkLog workLog = service.findById(id);
		model.addAttribute("record", workLog);
		return "record-edit";
	}
	@PostMapping("/records")
	public String save(@ModelAttribute WorkLog workLog) {
		service.save(workLog);
		return "redirect:/records";
	}
}
