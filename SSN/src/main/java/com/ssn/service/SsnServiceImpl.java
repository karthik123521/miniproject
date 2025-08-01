package com.ssn.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class SsnServiceImpl implements SsnService{

	@Override
	public String giveState(Long ssn) {
		Map<String, String> map = new HashMap<>();
		
		String numAsString = String.valueOf(ssn);
		
		if (numAsString.charAt(0) == '4' || numAsString.charAt(0) == '1') {
			map.put(numAsString, "New Jersey");
		} else if (numAsString.charAt(0) == '2' || numAsString.charAt(0) == '3') {
			map.put(numAsString, "Ohio");
		} else if (numAsString.charAt(0) == '5' || numAsString.charAt(0) == '6') {
			map.put(numAsString, "California");
		} else if (numAsString.charAt(0) == '7' || numAsString.charAt(0) == '8') {
			map.put(numAsString, "Texas");
		} else if (numAsString.charAt(0) == '9') {
			map.put(numAsString, "Boston");
		}
		return map.getOrDefault(numAsString, "Not Found");
	}
}
