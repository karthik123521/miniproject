package com.project.cat.Binding;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseWorkerBinding {

	private String fullName;

	private String email;

	private Long mobile;

	private Character gender;

	private LocalDate dateOfBirth;

	private Long ssn;

	private String password;
	
	private Boolean accStatus;

}
