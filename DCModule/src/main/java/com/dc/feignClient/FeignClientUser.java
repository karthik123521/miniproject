package com.dc.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "AR", url = "http://localhost:8085")
public interface FeignClientUser {

	@GetMapping("/AppId/{id}")
	public ResponseEntity<Boolean> searchAppId(@PathVariable("id") Integer id);
	
}
