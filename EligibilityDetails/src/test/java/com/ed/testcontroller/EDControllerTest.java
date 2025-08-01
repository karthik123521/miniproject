package com.ed.testcontroller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.ed.Service.EducationService;
import com.ed.controller.EDController;
import com.ed.dto.EligResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EDController.class) // Load only EDController & MVC layer
//@SpringBootTest
public class EDControllerTest {

	@Autowired
	private MockMvc mockMvc; /*
								 * mock HTTP requests and responses, Helps simulate HTTP calls without starting
								 * a real server.
								 */

	@MockBean
	private EducationService educationService; /*
												 * Creates a mock version of your service, This is injected into your
												 * controller so you can control what the service returns.
												 */

	@Autowired
	private ObjectMapper objectMapper;/*
										 * used to convert Java objects into JSON and vice versa. Not used directly here
										 * but often helpful for POST and PUT testing.
										 */

	@Nested
	@DisplayName("Endpoint: /checkElig/{id}")
	class CheckEligEndpoint {

		@Test
		@DisplayName("GET /checkElig/{id} returns 200 and expected JSON")
		void checkElig_happyPath() throws Exception {
			// Arrange

			EligResponse mockResp = new EligResponse();
			mockResp.setPlanStatus(true);
			mockResp.setBenefitAmount(5000.00);
			mockResp.setPlanName("CCAP");
			mockResp.setCaseNum(101);
			mockResp.setDenialReason(null);

			EligResponse mockResp2 = new EligResponse();
			mockResp2.setPlanStatus(false);
			mockResp2.setBenefitAmount(0.00);
			mockResp2.setPlanName("CCAP");
			mockResp2.setCaseNum(102);
			mockResp2.setDenialReason("No child found");

			when(educationService.chechEligibility(eq(101))).thenReturn(mockResp);

			when(educationService.getEligibility(eq(102))).thenReturn(mockResp2);

			// Act + Assert
			mockMvc.perform(get("/checkElig/101")) // Mocks an HTTP GET request to the given URL.
					.andExpect(status().isOk()).andExpect(jsonPath("$.planStatus").value(true))
					.andExpect(jsonPath("$.benefitAmount").value(5000.00))
					.andExpect(jsonPath("$.planName").value("CCAP")).andExpect(jsonPath("$.caseNum").value(101));

			mockMvc.perform(get("/getkElig/102")).andExpect(jsonPath("$.planStatus").value(false))
					.andExpect(jsonPath("$.benefitAmount").value(0.00)).andExpect(jsonPath("$.planName").value("CCAP"))
					.andExpect(jsonPath("$.caseNum").value(102)).andExpect(status().isOk());
		}

	}
	/*
	 * | Term | Example | Explanation | | ---------- |
	 * ----------------------------------------------------------- |
	 * ------------------------------------- | | Raw value | `101`, `"hello"` |
	 * Plain literal values | | Matcher | `eq(101)`, `anyInt()` | Mockito utility
	 * for flexible matching | | Don’t mix! | Use **either all raw** or **all
	 * matchers** in a method call | |
	 */
	/*
	 * | Feature | XPath | JSONPath | | -------------- | ------------------------ |
	 * ----------------- | | For format | XML | JSON | | Syntax example |
	 * `/person/name` | `$.person.name` | | Use case | Parsing XML, Web testing |
	 * Testing REST APIs |
	 */
}
