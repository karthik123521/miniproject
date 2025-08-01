package com.dc.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dc.binding.ChildRequestBinding;
import com.dc.binding.DCCaseBinding;
import com.dc.binding.EducationBinding;
import com.dc.binding.IncomeBinding;
import com.dc.binding.PlanSelectionBinding;
import com.dc.binding.Summaryfinal;
import com.dc.service.DcService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/dc")
public class DcController {

	public DcService dcService;

	public DcController(DcService dcService) {
		super();
		this.dcService = dcService;
	}

	@GetMapping("/AppId/{id}")
	ResponseEntity<Integer> findAppId(@PathVariable Integer id) {
		Integer appId = dcService.getAppId(id);
		return new ResponseEntity<Integer>(appId, HttpStatus.OK);
	}
	
	@GetMapping("/GetAllPlans")
	ResponseEntity<Map<Integer, String>> findAllPlans() {
		Map<Integer, String> planesNames = dcService.getPlans();
		return new ResponseEntity<Map<Integer, String>>(planesNames, HttpStatus.OK);
	}
	
	@PostMapping("/savePlan")
	public ResponseEntity<Integer> savePlan(@RequestBody PlanSelectionBinding plan){
		Integer savePlanSelection = dcService.savePlanSelection(plan);
		if(savePlanSelection!=null) {
			return new ResponseEntity<Integer>(savePlanSelection, HttpStatus.OK);
		}
		return ResponseEntity.noContent().build();
	}
	
	
	
	@PostMapping("/saveIncome")
	public ResponseEntity<Integer> addIncomeData(@RequestBody IncomeBinding income){
		Integer saveIncomeData = dcService.saveIncomeData(income);
		if(saveIncomeData!=null) {
			return new ResponseEntity<Integer>(saveIncomeData, HttpStatus.OK);
		}
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/saveEducation")
	public ResponseEntity<Integer> saveEducation(@RequestBody EducationBinding educationBinding){
		Integer saveEducation = dcService.saveEducation(educationBinding);
		if(saveEducation!=null) {
			return new ResponseEntity<Integer>(saveEducation, HttpStatus.OK);
		}
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/saveChildrenData")
	ResponseEntity<ChildRequestBinding> saveChildren(@RequestBody ChildRequestBinding childRequestBinding){
		ChildRequestBinding saveChildrenData = dcService.saveChildrenData(childRequestBinding);
		if(saveChildrenData!=null) {
			return new ResponseEntity<ChildRequestBinding>(saveChildrenData, HttpStatus.OK);
		}
		return ResponseEntity.noContent().build();
	}
	
	
	@GetMapping("/path/{param}")
	public ResponseEntity<Summaryfinal> getMethodName(@PathVariable Integer param) {
		Summaryfinal summary = dcService.getSummary(param);
		return new ResponseEntity<Summaryfinal>(summary,HttpStatus.OK);
	}
	
	@GetMapping("/getDCCaseEntity/{id}")
	public ResponseEntity<DCCaseBinding> get(@PathVariable Integer id) {
		DCCaseBinding getcase = dcService.getcase(id);
		return new ResponseEntity<DCCaseBinding>(getcase,HttpStatus.OK);
	}
	
	@GetMapping("/1")
	public String sample() {
		return "Hello String security";
	}
	
	
}
