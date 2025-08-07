package com.project.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.project.login.Util.EmailUtils;

@SpringBootApplication
public class LoginApplication {
	
	@Autowired
	private EmailUtils utils;

	
	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(LoginApplication.class, args);
	}

}
