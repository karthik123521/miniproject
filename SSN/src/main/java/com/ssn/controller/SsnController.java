package com.ssn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssn.service.SsnService;


@RestController
public class SsnController {

	@Autowired
	private SsnService ssnService;
	
	@GetMapping("/state/{ssn}")
	public ResponseEntity<String> giveState(@PathVariable("ssn") Long ssn){
		String state = ssnService.giveState(ssn);
		
		return new ResponseEntity<String>(state, HttpStatus.OK);
		
	}
	
	@GetMapping("/")
	public ResponseEntity<String> login(){
		return new ResponseEntity<>("Welcome to my bank", HttpStatus.OK);
		
	}
}
