package com.project.rep.Swagger;


import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder().group("Report").pathsToMatch("/**").build();
	}

}
