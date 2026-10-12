package com.farmview.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.farmview.entity.WorkLog;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {
	
}
