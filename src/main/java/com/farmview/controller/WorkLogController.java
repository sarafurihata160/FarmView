package com.farmview.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	
	@GetMapping("/records/new")
	public String recordForm(Model model) {
		model.addAttribute("workLog", new WorkLog());
		return "record-form";
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
	
	@PostMapping("/records/{id}/edit")
	public String update(
			@PathVariable Long id,
			@Valid @ModelAttribute("record") WorkLog workLog,
			BindingResult result) {
		
		if (result.hasErrors()) {
			return "record-edit";
		}
		
		workLog.setId(id);
		service.save(workLog);
		
		return "redirect:/records/" + id;
	}
	
	@PostMapping("/records/{id}/delete")
	public String delete(@PathVariable Long id) {
		service.deleteById(id);
		return "redirect:/records";
	}
	
	@PostMapping("/records")
	public String save(
			@Valid @ModelAttribute WorkLog workLog,
			BindingResult result) {
		
		if (result.hasErrors()) {
			return "record-form";
		}
		
		service.save(workLog);
		return "redirect:/records";
	}
}
