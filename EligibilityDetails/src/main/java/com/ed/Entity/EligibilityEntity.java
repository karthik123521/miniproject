package com.ed.Entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class EligibilityEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer edTraceId;
	
	private Integer caseNum;
	
	private String holderName;
	
	private String planName;
	
	private Boolean planStatus;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private Double benefitAmount;
	
	private String denialReason;
	
	
}
