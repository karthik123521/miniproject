package com.ed.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ed.Service.EducationService;
import com.ed.dto.EligResponse;

@RestController
public class EDController {

	public EducationService educationService;

	public EDController(EducationService educationService) {
		super();
		this.educationService = educationService;
	}
	
	@GetMapping("/checkElig/{id}")
	public ResponseEntity<EligResponse> chechElig(@PathVariable Integer id){
		EligResponse chechEligibility = educationService.chechEligibility(id);
		return new ResponseEntity<EligResponse>(chechEligibility,HttpStatus.OK);
	}
	
	@GetMapping("/getkElig/{id}")
	public ResponseEntity<EligResponse> getElig(@PathVariable Integer id){
		EligResponse chechEligibility = educationService.getEligibility(id);
		return new ResponseEntity<EligResponse>(chechEligibility,HttpStatus.OK);
	}
}
