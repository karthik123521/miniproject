package com.ss.repo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {

	@Id
	@GeneratedValue(generator = "CaseWork_User")
	private Integer id;
	private String userName;
	private String password;
}
