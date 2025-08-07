package com.ar.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ar.binding.CitizenApp;
import com.ar.service.ArService;

@RestController
public class ArRestController {

	@Autowired
	private ArService arService;
	
	
	
	public ArRestController(ArService arService) {
		super();
		this.arService = arService;
	}

	@PostMapping("/app")
	public ResponseEntity<String> createCitizenApp(@RequestBody CitizenApp app){
		Integer appId = arService.createApplication(app);
		if(appId > 0) {
			return new ResponseEntity<> ("App Created with App Id:  " + appId, HttpStatus.OK);
			
		}else {
			
			return new ResponseEntity<> ("Invalid SSN", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/AppId/{id}")
	public ResponseEntity<Boolean> searchAppId(@PathVariable Integer id){
		Boolean checkAppId = arService.checkAppId(id);
		return new ResponseEntity<Boolean>(checkAppId,HttpStatus.OK);
	}
	
	@GetMapping("/getDob/{id}")
	public ResponseEntity<LocalDate> findDate(@PathVariable Integer id){
		LocalDate dob = arService.findDob(id);
		return new ResponseEntity<LocalDate>(dob,HttpStatus.OK);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<CitizenApp> find(@PathVariable Integer id){
		CitizenApp id2 = arService.findId(id);
		return new ResponseEntity<CitizenApp>(id2,HttpStatus.OK);
	}
}
