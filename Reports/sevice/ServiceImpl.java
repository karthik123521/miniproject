package com.Reports.sevice;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.Reports.Entity.EligibilityDtls;
import com.Reports.Repository.EligibilityDtlsRepo;
import com.Reports.binding.SearchRequest;
import com.Reports.binding.SearchResponse;
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

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ServiceImpl implements ReportService {

	private EligibilityDtlsRepo eligibilityDtlsRepo;

	public ServiceImpl(EligibilityDtlsRepo eligibilityDtlsRepo) {

		this.eligibilityDtlsRepo = eligibilityDtlsRepo;
	}

	@Override
	public List<String> getUniquePlanNames() {
		List<String> planNames = eligibilityDtlsRepo.findPlanNames();
		return planNames;

	}

	@Override
	public List<String> getUniquePlanStatuses() {
		List<String> planStatuses = eligibilityDtlsRepo.findPlanStatuses();
		return planStatuses;

	}

	@Override
	public List<SearchResponse> search(SearchRequest request) {
		List<SearchResponse> response = new ArrayList<>();
		System.out.println(request);
		EligibilityDtls queryBuliderExample = new EligibilityDtls();

		if (request.getPlanName() != null && !request.getPlanName().equals(""))
			queryBuliderExample.setPlanName(request.getPlanName());

		if (request.getPlanStatus() != null && !request.getPlanStatus().equals(""))
			queryBuliderExample.setPlanStatus(request.getPlanStatus());

		if (request.getPlanStartDate() != null)
			queryBuliderExample.setPlan_Start_Date(request.getPlanStartDate());

		if (request.getPlanEndDate() != null)
			queryBuliderExample.setPlan_End_Date(request.getPlanEndDate());
		System.out.println(queryBuliderExample);
		Example<EligibilityDtls> example = Example.of(queryBuliderExample);
		List<EligibilityDtls> all = eligibilityDtlsRepo.findAll(example);
		for (EligibilityDtls eg : all) {
			SearchResponse sr = new SearchResponse();
			BeanUtils.copyProperties(eg, sr);
			response.add(sr);

		}

		return response;

	}

	@Override
	public void generateExcel(HttpServletResponse response) throws IOException {
		List<EligibilityDtls> entities = eligibilityDtlsRepo.findAll();

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow headerRow = sheet.createRow(0);

		headerRow.createCell(0).setCellValue("Name");
		headerRow.createCell(1).setCellValue("Email");
		headerRow.createCell(2).setCellValue("Gender");
		headerRow.createCell(3).setCellValue("MobileNo");
		headerRow.createCell(4).setCellValue("SSN");
		headerRow.createCell(5).setCellValue("State");

		int i = 1;
		for (EligibilityDtls entity : entities) {
			HSSFRow dataRow = sheet.createRow(i);
			dataRow.createCell(0).setCellValue(entity.getName());
			dataRow.createCell(1).setCellValue(entity.getEmail());
			dataRow.createCell(2).setCellValue(String.valueOf(entity.getGender()));
			dataRow.createCell(3).setCellValue(entity.getMoblieNum());
			dataRow.createCell(4).setCellValue(entity.getSsn());
			dataRow.createCell(5).setCellValue(entity.getState());
			i++;
		}
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}

	@Override
	public void generatePdf(HttpServletResponse response) throws DocumentException, IOException {
		List<EligibilityDtls> entities = eligibilityDtlsRepo.findAll();

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);

		Paragraph p = new Paragraph("SEARCH REPORT", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(p);

		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.5f, 3.5f, 3.0f, 1.5f, 3.0f });
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
		
		cell.setPhrase(new Phrase("State", font));
		table.addCell(cell);

		for (EligibilityDtls entity : entities) {
			table.addCell(entity.getName());
			table.addCell(entity.getEmail());
			table.addCell(String.valueOf(entity.getMoblieNum()));
			table.addCell(String.valueOf(entity.getGender()));
			table.addCell(String.valueOf(entity.getSsn()));
			table.addCell(String.valueOf(entity.getState()));
		}
		document.add(table);
		document.close();
	}

}
