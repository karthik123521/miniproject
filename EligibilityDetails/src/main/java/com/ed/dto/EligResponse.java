package com.ed.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EligResponse {
	
	private Integer caseNum;
	
	private String planName;
	
	private Boolean planStatus;
	
	private Double benefitAmount;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private String denialReason;
	

}
