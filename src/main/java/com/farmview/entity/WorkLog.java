package com.farmview.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class WorkLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "作業日を入力してください")
	private LocalDate workDate;
	
	@NotBlank(message = "作物名を入力してください")
	private String crop;
	
	@NotBlank(message = "作業内容を入力してください")
	private String workContent;
	
	private String fieldName;
	private String memo;
}
