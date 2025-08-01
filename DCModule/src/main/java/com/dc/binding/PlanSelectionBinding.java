package com.dc.binding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanSelectionBinding {
	
	private Integer caseNum;
	private String planName;
	private Integer planId;

}
