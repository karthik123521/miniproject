package com.project.login.Binding;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class LoginBinding {

	private String fullName;

	private String emailId;

	private Long mobileNum;

	private String gender;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;

	private Long ssn;

}
