package com.ed.Service;

import com.ed.dto.EligResponse;

public interface EducationService {

	public EligResponse chechEligibility(Integer caseNum);
	
	public EligResponse getEligibility(Integer caseNum);
}
