package com.ed.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ed.binding.Summaryfinal;

@FeignClient(name = "DCModule", url = "http://localhost:8086/dc")
public interface FeignClientImpl {
	
	@GetMapping("/path/{param}")
	public ResponseEntity<Summaryfinal> getMethodName(@PathVariable Integer param);
	
	
	

}
