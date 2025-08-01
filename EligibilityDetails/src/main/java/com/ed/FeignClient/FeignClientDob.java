package com.ed.FeignClient;

import java.time.LocalDate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ed.binding.CitizenApp;

@FeignClient(name = "AR", url = "http://localhost:8085")
public interface FeignClientDob {

	@GetMapping("/getDob/{id}")
	public ResponseEntity<LocalDate> findDate(@PathVariable Integer id);
	
	@GetMapping("/get/{id}")
	public ResponseEntity<CitizenApp> find(@PathVariable Integer id);
}
