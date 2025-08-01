package com.dc.service;

import java.util.Map;

import com.dc.binding.ChildRequestBinding;
import com.dc.binding.DCCaseBinding;
import com.dc.binding.EducationBinding;
import com.dc.binding.IncomeBinding;
import com.dc.binding.PlanSelectionBinding;
import com.dc.binding.Summaryfinal;

public interface DcService {
	
	public Integer getAppId(Integer id);
	
	public Map<Integer, String> getPlans();
	
	public Integer saveIncomeData(IncomeBinding incomeBinding);

	public Integer saveEducation(EducationBinding educationBinding);

	public ChildRequestBinding saveChildrenData(ChildRequestBinding childRequestBinding);
	
	public Integer savePlanSelection(PlanSelectionBinding planSelectionBinding);
	
	public Summaryfinal getSummary(Integer caseNum);
	
	public DCCaseBinding getcase(Integer id);

}
