package com.project.login.Entity;



import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LoginEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer loginId;
	
	private String fullName;
	
	private String tempPassword;
	
	private String password;
	
	private String emailId;
	
	private Long mobileNum;
	
	private String gender;
	
	private Date dateOfBirth;
	
	private Long ssn;
	
	private Boolean accStatus;
	
	@CreationTimestamp
	private Date createdDate;
	
	private String createdBy;
	
	@UpdateTimestamp
	private Date updatedDate;
	
	private String updatedBy;
	
	
}
