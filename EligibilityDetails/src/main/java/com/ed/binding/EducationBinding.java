package com.ed.binding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationBinding {
	
	private Integer caseNumber;
	private String higherDegree;
	private Integer gradYear;
	private String univName;

}
