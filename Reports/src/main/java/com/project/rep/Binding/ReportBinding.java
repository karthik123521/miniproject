package com.project.rep.Binding;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ReportBinding {
	
	private Integer eligId;
	
	private String name;
	
	private Long mobileNum;
	
	private String email;
	
	private Long ssn;
	
	private String gender;
	
	private String planName;
	
	private String planStatus;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
	
	@CreationTimestamp
	private LocalDate createDate;

	@UpdateTimestamp
	private LocalDate updateDate;

	private String createdBy;

	private String updatedBy;

}
