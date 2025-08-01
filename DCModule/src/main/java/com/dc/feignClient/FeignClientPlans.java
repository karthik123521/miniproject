package com.dc.feignClient;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "plans-api", url = "http://localhost:8080")
public interface FeignClientPlans {

	@GetMapping("/getActiveCatagories")
	public ResponseEntity<Map<Integer, String>>findAllPlans();
}


