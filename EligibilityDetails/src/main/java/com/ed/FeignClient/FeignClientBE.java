package com.ed.FeignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ed.dto.EligResponse;


@FeignClient(name = "BE", url = "http://localhost:8092")
public interface FeignClientBE {
	
	@PostMapping("/sendMail")
	public ResponseEntity<String> sendMailToBank(@RequestBody List<EligResponse> bEBinding);

}
