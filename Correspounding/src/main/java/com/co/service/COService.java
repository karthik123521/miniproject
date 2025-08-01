package com.co.service;

import com.co.binding.CoResponse;
import com.co.binding.CoTriggerBinding;

public interface COService {
	
	public CoTriggerBinding saveData(CoTriggerBinding coTriggerBinding); 

	public CoResponse processPendingTriggers();
}
