package com.Reports.binding;

import lombok.Data;

@Data
public class SearchResponse {

	private String name;
	private Long moblieNum;
	private String email;
	private Character gender;
	private Long ssn;
	private String planName;
	private String planStatus;
	private String State;
}
