package com.co.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.co.binding.CitizenApp;

@FeignClient(name = "AR", url = "http://localhost:8085")
public interface FeignClientAR {

	@GetMapping("/get/{id}")
	public ResponseEntity<CitizenApp> find(@PathVariable Integer id);
	
	/*@GetMapping("/get/{id}")
	public ResponseEntity<CitizenApp> find(@PathVariable Integer id){
		CitizenApp id2 = arService.findId(id);
		return new ResponseEntity<CitizenApp>(id2,HttpStatus.OK);
	}*/
}
