package com.farmview.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.farmview.entity.WorkLog;
import com.farmview.repository.WorkLogRepository;

@Service
public class WorkLogService {

	private final WorkLogRepository repository;
	
	public WorkLogService(WorkLogRepository repository) {
		this.repository = repository;
	}
	
	public void save(WorkLog workLog) {
		repository.save(workLog);
	}
	public List<WorkLog> findAll() {
		return repository.findAll();
	}
	public WorkLog findById(Long id) {
		return repository.findById(id).orElse(null);
	}
}
