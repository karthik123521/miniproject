package com.ed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EligibilityDetailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EligibilityDetailsApplication.class, args);
	}

}
