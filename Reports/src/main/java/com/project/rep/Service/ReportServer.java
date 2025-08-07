package com.project.rep.Service;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.project.rep.Binding.DisplayReport;
import com.project.rep.Binding.ReportBinding;
import com.project.rep.Binding.SearchRequest;
import com.project.rep.Entity.ReportEntity;
import com.project.rep.EntityRepo.EntityRepos;
import com.project.rep.ServiceRepo.ServiceRepos;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ReportServer implements ServiceRepos {

	private EntityRepos er;

	public ReportServer(EntityRepos er) {
		super();
		this.er = er;
	}

	@Override
	public List<String> getUniquePlanName() {
		List<String> names = er.findAllPlanName();
		List<String> uniqueNames = names.stream().distinct().collect(Collectors.toList());
		return uniqueNames;
	}

	@Override
	public List<String> getUniquePlanStatus() {
		List<String> allPlanStatus = er.findAllPlanStatus();
		List<String> collect = allPlanStatus.stream().distinct().collect(Collectors.toList());
		return collect;
	}

	@Override
	public void generateExcel(HttpServletResponse httpExcel) {
		
		List<ReportEntity> all = er.findAll();
		
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow row = sheet.createRow(0);
		
		row.createCell(0).setCellValue("Name");
		row.createCell(1).setCellValue("Email");
		row.createCell(2).setCellValue("Gender");
		row.createCell(3).setCellValue("MobileNum");
		row.createCell(4).setCellValue("SSN");
		
		int i=0;
		for(ReportEntity ds: all) {
			HSSFRow row2 = sheet.createRow(i);
			row2.createCell(0).setCellValue(ds.getName());
			row2.createCell(1).setCellValue(ds.getEmail());
			row2.createCell(2).setCellValue(String.valueOf(ds.getGender()));
			row2.createCell(3).setCellValue(ds.getMobileNum());
			row2.createCell(4).setCellValue(ds.getSsn());
			i++;
			
			}
		ServletOutputStream outputStream = null;
		try {
			outputStream = httpExcel.getOutputStream();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		try {
			workbook.write(outputStream);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		try {
			workbook.close();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		
			e.printStackTrace();
		}

	}

	@Override
	public void generatePdf(HttpServletResponse httpExcel) {
		List<ReportEntity> all = er.findAll();
		
		Document document = new Document(PageSize.A4);
		try {
			PdfWriter.getInstance(document, httpExcel.getOutputStream());
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);
		
		Paragraph p = new Paragraph("SEARCH REPORT", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(p);
		
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 1.5f, 3.0f});
		table.setSpacingBefore(10);
		
		PdfPCell cell = new PdfPCell();
		
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
		
		font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		
		cell.setPhrase(new Phrase("Name", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Email", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Mobile No", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Gender", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("SSN", font));
		table.addCell(cell);
		
		for(ReportEntity entity : all) {
			table.addCell(entity.getName());
			table.addCell(entity.getEmail());
			table.addCell(String.valueOf(entity.getMobileNum()));
			table.addCell(String.valueOf(entity.getGender()));
			table.addCell(String.valueOf(entity.getSsn()));
		}
		document.add(table);
		document.close();
	}

	@Override
	public DisplayReport postReport(ReportBinding rb) {

		ReportEntity re = new ReportEntity();
		re.setName(rb.getName());
		re.setCreateDate(rb.getCreateDate());
		re.setCreatedBy(rb.getCreatedBy());
		re.setEligId(rb.getEligId());
		re.setEmail(rb.getEmail());
		re.setEndDate(rb.getEndDate());
		re.setGender(rb.getGender());
		re.setMobileNum(rb.getMobileNum());
		re.setPlanName(rb.getPlanName());
		re.setPlanStatus(rb.getPlanStatus());
		re.setSsn(rb.getSsn());
		re.setStartDate(rb.getStartDate());
		re.setUpdateDate(rb.getUpdateDate());
		re.setUpdatedBy(rb.getUpdatedBy());

		DisplayReport dr = new DisplayReport();
		BeanUtils.copyProperties(re, dr);
		er.save(re);

		return dr;
	}

	@Override
	public List<DisplayReport> search(SearchRequest binding) {

		ReportEntity entity = new ReportEntity();
		entity.setPlanName(binding.getPlanName());
		entity.setPlanStatus(binding.getPlanStatus());

		Example<ReportEntity> ex= Example.of(entity);
		List<ReportEntity> all = er.findAll(ex);
		List<DisplayReport> list=new ArrayList<>();
		for(ReportEntity rep : all) {
			DisplayReport disp=new DisplayReport();
			BeanUtils.copyProperties(rep,disp);
			list.add(disp);
		}

		return list;
	}

}
