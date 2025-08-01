package com.ed.binding;

import java.util.List;

import lombok.Data;

@Data
public class Summaryfinal {
	
	private EducationBinding education;
	private IncomeBinding income;
	private List<ChildBinding> child;
	private Integer appId;
	private String planName;

}
