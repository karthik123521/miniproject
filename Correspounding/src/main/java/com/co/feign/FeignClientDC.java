package com.co.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.co.binding.DCCaseBinding;


@FeignClient(name = "Login", url = "http://localhost:8086/dc")
public interface FeignClientDC {
	
	@GetMapping("/getDCCaseEntity/{id}")
	public ResponseEntity<DCCaseBinding> get(@PathVariable Integer id);
	
}
