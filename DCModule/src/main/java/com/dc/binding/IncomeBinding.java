package com.dc.binding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeBinding {
	
	private Integer caseNumber;
	private Double monthlySalaryIncome;
	private Double rentIncome;
	private Double propertyIncome;
}
