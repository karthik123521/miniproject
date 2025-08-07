package com.project.rep.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.rep.Binding.DisplayReport;
import com.project.rep.Binding.ReportBinding;
import com.project.rep.Binding.SearchRequest;
import com.project.rep.ServiceRepo.ServiceRepos;

import jakarta.servlet.http.HttpServletResponse;


@RestController
public class ReportContrller {

	private ServiceRepos repo;

	public ReportContrller(ServiceRepos repo) {
		super();
		this.repo = repo;
	}

	@PostMapping("/postData")
	public ResponseEntity<DisplayReport> postMapping(@RequestBody ReportBinding bind) {
		DisplayReport postReport = repo.postReport(bind);
		return new ResponseEntity<DisplayReport>(postReport, HttpStatus.OK);
	}
	
	@GetMapping("/getNames")
	public ResponseEntity<List<String>> getMethodName() {
		List<String> uniquePlanName = repo.getUniquePlanName();
		return new ResponseEntity<List<String>>(uniquePlanName,HttpStatus.OK);
	}
	
	@GetMapping("/getStatus")
	public ResponseEntity<List<String>> getMethodStatus() {
		List<String> uniquePlanStatus = repo.getUniquePlanStatus();
		return new ResponseEntity<List<String>>(uniquePlanStatus,HttpStatus.OK);
	}
	
	@GetMapping("/Search")
	public ResponseEntity<List<DisplayReport>> search(@RequestBody SearchRequest se) {
		List<DisplayReport> search = repo.search(se);
		return new ResponseEntity<List<DisplayReport>>(search,HttpStatus.OK);
	}
	
	@GetMapping("/excel")
	public void generateExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename = data.xls";
		response.setHeader(headerKey, headerValue);
		
		repo.generateExcel(response);
	}
	
	@GetMapping("/pdf")
	public void generatePdf(HttpServletResponse response) throws IOException {
		response.setContentType("application/pdf");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename = data.pdf";
		response.setHeader(headerKey, headerValue);
		
		repo.generatePdf(response);
	}
	

}
