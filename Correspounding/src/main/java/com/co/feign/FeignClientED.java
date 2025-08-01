package com.co.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.co.binding.EligResponse;

@FeignClient(name = "ED", url = "http://localhost:8087")
public interface FeignClientED {

	@GetMapping("/getkElig/{id}")
	public ResponseEntity<EligResponse> getElig(@PathVariable Integer id);
}
