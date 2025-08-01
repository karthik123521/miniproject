package com.Reports.Entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name="Eligibility_Details")
@ToString
public final class EligibilityDtls {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer eligiblityId;
	private String name;
	private Long moblieNum;
	private String email;
	private Character gender;
	private Long ssn;
	private String planName;
	private String planStatus;
	private LocalDate Plan_Start_Date;
	private LocalDate Plan_End_Date;
	private LocalDate Plan_Craete_Date;
	private LocalDate Plan_Update_Date;
	private String createdBy;
	private String updatedBy;
	private String State;
	
	
	
	
}
