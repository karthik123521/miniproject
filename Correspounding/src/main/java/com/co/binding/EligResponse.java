package com.co.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EligResponse {
	
	private Integer caseNum;
	
	private String planName;
	
	private Boolean planStatus;
	
	private Double benefitAmount;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private String denialReason;
	

}
