package com.project.cat.Binding;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Transient;
import lombok.Data;

@Data
public class PlanMasterBinding {


	private Integer planId;

	private String planName;

	private LocalDate planStartDate;

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
