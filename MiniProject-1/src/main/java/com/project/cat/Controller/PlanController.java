package com.project.cat.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.cat.Binding.PlanBinding;
import com.project.cat.Binding.PlanCatagoryBinding;
import com.project.cat.Binding.PlanMasterBinding;
import com.project.cat.Entity.PlanMasterEntity;
import com.project.cat.Service.PlanCatService;
import com.project.cat.Service.PlanService;
import com.project.cat.constants.AppConstants;

@RestController
//@RequestMapping("/update")
public class PlanController {

	private PlanCatService planCat;
	private PlanService planMaster;

	public PlanController(PlanCatService planCat, PlanService planMaster) {
		this.planCat = planCat;
		this.planMaster = planMaster;
	}

	@PostMapping("/NewCatagory")
	public ResponseEntity<String> postCatagory(@RequestBody PlanCatagoryBinding plan) {
		String postPlanCat = planCat.postPlanCat(plan);
		return new ResponseEntity<>(postPlanCat, HttpStatus.ACCEPTED);

	}

	@GetMapping("/getActiveCatagories")
	public ResponseEntity<Map<Integer, String>> getCat() {
		Map<Integer, String> planCategories = planMaster.getPlanCategories();
		return new ResponseEntity<Map<Integer, String>>(planCategories, HttpStatus.OK);
	}

	@GetMapping("/GetPlanById/{in}")
	public ResponseEntity<PlanMasterEntity> findById(@PathVariable Integer in) {
		PlanMasterEntity planById = planMaster.getPlanById(in);
		return new ResponseEntity<PlanMasterEntity>(planById, HttpStatus.FOUND);

	}

	@PostMapping("/PostANewPlan")
	public ResponseEntity<PlanMasterEntity> postPlans(@RequestBody PlanMasterBinding pe) {
		PlanMasterEntity postPlan = planMaster.postPlan(pe);
		return new ResponseEntity<PlanMasterEntity>(postPlan, HttpStatus.ACCEPTED);

	}

	@GetMapping("/getAllPlans")
	public ResponseEntity<Map<Integer, String>> getAllPlans() {
		Map<Integer, String> map = new HashMap<>();
		map.put(1, "SNAP");
		map.put(2, "CCAP");
		map.put(3, "MEDICARE");
		map.put(4, "MEDICATE");
		map.put(5, "NJW");
		return ResponseEntity.ok(map);
	}

	@PutMapping("/updatePlan")
	public ResponseEntity<String> updatePlan(@RequestBody PlanMasterEntity plan) {
		String updatePlan = planMaster.updatePlan(plan);
		return new ResponseEntity<String>(updatePlan, HttpStatus.ACCEPTED);
	}

	@PutMapping("/updateStatus/{planId}/{activeSw}")
	public ResponseEntity<String> statusChange(@PathVariable Integer planId, @PathVariable Boolean activeSw) {
		boolean planStatusChange = planMaster.planStatusChange(planId, activeSw);
		if (planStatusChange) {
			return new ResponseEntity<String>(AppConstants.PLAN_UPDATE_SUCC, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<String>(AppConstants.PLAN_UPDATE_FAIL, HttpStatus.ACCEPTED);
		}
	}

	@DeleteMapping("/deletePlan")
	public ResponseEntity<String> deleteById(@RequestBody PlanMasterEntity plan) {
		boolean deletePlanById = planMaster.deletePlanById(plan);
		if (deletePlanById) {
			return new ResponseEntity<String>(AppConstants.PLAN_DELETE_SUCC, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<String>(AppConstants.PLAN_DELETE_FAIL, HttpStatus.ACCEPTED);
		}
	}

}
