package com.project.cat.Entity;

import java.time.LocalDate;
import java.util.Map;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table
public class PlanMasterEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer planId;

	private String planName;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate planStartDate;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate planEndDate;


	private Integer planCatId;
	

	private Boolean activeSwitch;

	@CreationTimestamp
	private LocalDate createDate;

	@UpdateTimestamp
	private LocalDate updateDate;

	private String createdBy;

	private String updatedBy;
}
