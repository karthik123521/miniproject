package com.co.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.co.binding.CoResponse;
import com.co.binding.CoTriggerBinding;
import com.co.service.COService;

@RestController
public class COController {

	@Autowired
    private COService triggerService;

    @PostMapping("/api/triggers")
    public ResponseEntity<?> saveTrigger(@RequestBody CoTriggerBinding triggerBinding) {
        CoTriggerBinding savedTrigger = triggerService.saveData(triggerBinding);

        if (savedTrigger != null) {
            return ResponseEntity.ok(savedTrigger);
        } else {
            return ResponseEntity
                .badRequest()
                .body("Trigger with this case number already exists.");
        }
    }
    @GetMapping("/process")
    public CoResponse processPendingTriggers() {
        return triggerService.processPendingTriggers();
    }
	
}
