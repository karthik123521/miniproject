package com.Reports.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.Reports.binding.SearchRequest;
import com.Reports.binding.SearchResponse;
import com.Reports.sevice.ReportService;

//import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class ReportsRestController {


	@Autowired
	private ReportService reportService;
	
	@GetMapping("/plan")
	public ResponseEntity<List<String>> getPlanName(){
		List<String> planNames = reportService.getUniquePlanNames();
		return new ResponseEntity<List<String>> (planNames,HttpStatus.OK);
	}
	
	@GetMapping("/status")
	public ResponseEntity<List<String>> getPlanStatus(){
		List<String> planStatuses = reportService.getUniquePlanStatuses();
		return new ResponseEntity<List<String>> (planStatuses,HttpStatus.OK);
	}
	
	@PostMapping("/search")
	public ResponseEntity<List<SearchResponse>> search(@RequestBody SearchRequest request) {
		System.out.println("controller"+" "+request);
	    List<SearchResponse> response = reportService.search(request);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
	@GetMapping("/excel")
	public void generateExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename = data.xls";
		response.setHeader(headerKey, headerValue);
		
		reportService.generateExcel(response);
	}
	
	@GetMapping("/pdf")
	public void generatePdf(HttpServletResponse response) throws IOException {
		response.setContentType("application/pdf");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename = data.pdf";
		response.setHeader(headerKey, headerValue);
		
		reportService.generatePdf(response);
	}
}
