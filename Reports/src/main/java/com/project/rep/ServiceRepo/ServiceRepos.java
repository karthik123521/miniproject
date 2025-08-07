package com.project.rep.ServiceRepo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.project.rep.Binding.DisplayReport;
import com.project.rep.Binding.ReportBinding;
import com.project.rep.Binding.SearchRequest;
import com.project.rep.Entity.ReportEntity;

import jakarta.servlet.http.HttpServletResponse;

@Repository
public interface ServiceRepos {
	
	public DisplayReport postReport(ReportBinding binding);

	public List<String> getUniquePlanName();
	
	public List<String> getUniquePlanStatus();
	
	public List<DisplayReport> search(SearchRequest binding);
	
	public void generateExcel(HttpServletResponse httpExcel);
	
	public void generatePdf(HttpServletResponse httpExcel);
}
