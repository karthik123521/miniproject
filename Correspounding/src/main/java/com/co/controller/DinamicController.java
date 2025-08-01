package com.co.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.binding.DinamicSchDto;
import com.co.service.DinamicSchImp;

@RestController
@RequestMapping("/set")
public class DinamicController {

	private DinamicSchImp dinamicSchImp;

	public DinamicController(DinamicSchImp dinamicSchImp) {
		super();
		this.dinamicSchImp = dinamicSchImp;
	}
	
	@PostMapping("/updateCorn")
	public ResponseEntity<String> updateCorn(@RequestBody DinamicSchDto dinamicSchDto){
		dinamicSchImp.updateCornExpression(dinamicSchDto);
		return ResponseEntity.ok("Corn Got updated");
	}
}
