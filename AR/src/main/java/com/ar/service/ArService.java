package com.ar.service;

import java.time.LocalDate;

import com.ar.binding.CitizenApp;

public interface ArService {

	public Integer createApplication(CitizenApp app);
	
	public Boolean checkAppId(Integer appId);
	
	public LocalDate findDob(Integer id);
	
	public CitizenApp findId(Integer id);
	
	}
