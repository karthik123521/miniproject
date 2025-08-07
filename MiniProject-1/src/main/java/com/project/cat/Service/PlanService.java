package com.project.cat.Service;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.project.cat.Binding.PlanBinding;
import com.project.cat.Binding.PlanCatagoryBinding;
import com.project.cat.Binding.PlanMasterBinding;
import com.project.cat.Entity.PlanMasterEntity;

@Repository
public interface PlanService {
	
	//public String postPlanCat(PlanCatagoryEntity planEntity);
	
	public Map<Integer, String> getPlanCategories();
	
	public PlanMasterEntity postPlan(PlanMasterBinding cat);
	
	public boolean savePlan(PlanMasterEntity plan);
	
	public Map<Integer, String> getAllPlans();
	
	public PlanMasterEntity getPlanById(Integer planId);
	
	public String updatePlan(PlanMasterEntity plan);
	
	public boolean deletePlanById(PlanMasterEntity plan);
	
	public boolean planStatusChange(Integer planId, Boolean activeSw);

}
