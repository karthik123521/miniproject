package com.ed.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ed.binding.CoTriggerBinding;


@FeignClient(name = "CO", url = "http://localhost:8090")
public interface FeignClientCO {

	@PostMapping("/api/triggers")
    public ResponseEntity<CoTriggerBinding> saveTrigger(@RequestBody CoTriggerBinding coResponse);
}
