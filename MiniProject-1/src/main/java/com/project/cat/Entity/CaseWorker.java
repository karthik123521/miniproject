package com.project.cat.Entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.UpdateTimestamp;

import com.project.cat.Idgenerator.CustomIdGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CaseWorker {
	
	@SuppressWarnings("deprecation")
	@Id
	@GeneratedValue(generator = "CaseWork_User")
	@GenericGenerator(
	    name = "CaseWork_User",
	    strategy = "com.project.cat.Idgenerator.CustomIdGenerator", // correct path
	    parameters = {
	        @Parameter(name = CustomIdGenerator.NUMBER_FORMAT_DEFAULT, value = "1"),
	        @Parameter(name = CustomIdGenerator.VALUE_PREFIX_PARAMETER_STRING, value = "UserCW_"),
	        @Parameter(name = CustomIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%02d")
	    }
	)
	private String userId;


	private String fullName;

	@Column(unique = true)
	private String email;

	private Long mobile;

	private Character gender;

	private LocalDate dateOfBirth;

	private Long ssn;

	private String password;

	private Boolean accStatus;
	
	@Column(name = "CREATED_DATE", updatable = false)
	@CreationTimestamp
	private LocalDate createdDate;

	@Column(name = "UPDATED_DATE", insertable = false)
	@UpdateTimestamp
	private LocalDate updatedDate;

	private String createdBy;

	private String updatedBy;

}
