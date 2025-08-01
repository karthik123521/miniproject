package com.co.binding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DinamicSchDto {

	private String taskName;
	private String cronExpression;
}
