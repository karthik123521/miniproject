package com.co.service;

import java.awt.Color;
//import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.co.binding.CitizenApp;
import com.co.binding.CoResponse;
import com.co.binding.CoTriggerBinding;
import com.co.binding.DCCaseBinding;
import com.co.binding.EligResponse;
import com.co.entity.CoTriggerEntity;
import com.co.entityRepo.CoTriggerEntityRepo;
import com.co.feign.FeignClientAR;
import com.co.feign.FeignClientDC;
import com.co.feign.FeignClientED;
import com.co.utils.EmailUtils;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import feign.FeignException;

@Service
public class COServiceImpl implements COService {

	public FeignClientAR feignClientAR;
	public FeignClientED feignClientED;
	public FeignClientDC feignClientDC;
	public CoTriggerEntityRepo coTriggerEntityRepo;
	public EmailUtils emailUtils;

	

	public COServiceImpl(FeignClientAR feignClientAR, FeignClientED feignClientED, FeignClientDC feignClientDC,
			CoTriggerEntityRepo coTriggerEntityRepo, EmailUtils emailUtils) {
		super();
		this.feignClientAR = feignClientAR;
		this.feignClientED = feignClientED;
		this.feignClientDC = feignClientDC;
		this.coTriggerEntityRepo = coTriggerEntityRepo;
		this.emailUtils = emailUtils;
	}

	@Override
	public CoResponse processPendingTriggers() {
	    CoResponse coResponse = new CoResponse();
	    List<CoTriggerEntity> byTrgStatus = coTriggerEntityRepo.findByTrgStatus("Pending");
	    coResponse.setTotalTriggers(Long.valueOf(byTrgStatus.size()));

	    Long success = 0L;
	    Long failed = 0L;

	    for (CoTriggerEntity entity : byTrgStatus) {
	        try {
	            // Fetch data from external services
	            EligResponse eligResponse = feignClientED.getElig(entity.getCaseNum()).getBody();
	            DCCaseBinding dcCaseBinding = feignClientDC.get(entity.getCaseNum()).getBody();
	            if (dcCaseBinding == null) {
	               // log.warn("No DCCaseBinding found for CaseNum: {}", entity.getCaseNum());
	                failed++;
	                continue;
	            }

	            Integer appId = dcCaseBinding.getAppId();
	            CitizenApp citizenApp = feignClientAR.find(appId).getBody();
	            if (citizenApp == null) {
	               // log.warn("No CitizenApp found for AppId: {}", appId);
	                failed++;
	                continue;
	            }

	            // Process the trigger (generate PDF)
	            try {
	                generateAndSendPdf(eligResponse, citizenApp);
	                
	                success++;
	            } catch (Exception e) {
	               // log.error("Failed to generate PDF for CaseNum: {}. Error: {}", entity.getCaseNum(), e.getMessage());
	                failed++;
	            }

	        } catch (FeignException e) {
	           // log.error("Feign call failed for CaseNum: {}. Error: {}", entity.getCaseNum(), e.getMessage());
	            failed++;
	        } catch (Exception e) {
	           // log.error("Unexpected error processing CaseNum: {}. Error: {}", entity.getCaseNum(), e.getMessage());
	            failed++;
	        }
	    }

	    coResponse.setSuccTriggers(success);
	    coResponse.setFailedTriggers(failed);

	    return coResponse;
	}


	@Override
	public CoTriggerBinding saveData(CoTriggerBinding coTriggerBinding) {
		Integer caseNum = coTriggerBinding.getCaseNum();
		CoTriggerEntity byCaseNum = coTriggerEntityRepo.findByCaseNum(caseNum);
		if(byCaseNum==null) {
			CoTriggerEntity coTriggerEntity=new CoTriggerEntity();
			coTriggerEntity.setCaseNum(coTriggerBinding.getCaseNum());
			coTriggerEntity.setTrgStatus(coTriggerBinding.getTrgStatus());
			CoTriggerEntity save = coTriggerEntityRepo.save(coTriggerEntity);
			CoTriggerBinding coTrigger=new CoTriggerBinding();
			BeanUtils.copyProperties(save, coTrigger);

			return coTrigger;
		}
		return null;
	}
	
	private void generateAndSendPdf(EligResponse eligData, CitizenApp appEntity) throws Exception {
		Document document = new Document(PageSize.A4);
		FileOutputStream fos = null;
		File file = new File(eligData.getCaseNum()+".pdf");
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		PdfWriter.getInstance(document, fos);
		
		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);
		
		Paragraph p = new Paragraph("ELIGIBILITY REPORT", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(p);
		
		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 1.5f, 3.0f, 1.5f, 3.0f});
		table.setSpacingBefore(10);
		
		PdfPCell cell = new PdfPCell();
		
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
		
		font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		
		cell.setPhrase(new Phrase("Citizen Name", font));
		table.addCell(cell);
			
		cell.setPhrase(new Phrase("Plan Name", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Plan Status", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Plan Start Date", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Plan End Date", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Benefit Amount", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Denial Reason", font));
		table.addCell(cell);
			table.addCell(appEntity.getFullName());
			table.addCell(eligData.getPlanName());
			Boolean planStatus = eligData.getPlanStatus();
			table.addCell(planStatus+"");
			table.addCell(eligData.getStartDate()+"");
			table.addCell(eligData.getEndDate()+"");
			table.addCell(eligData.getBenefitAmount()+"");
			table.addCell(eligData.getDenialReason()+"");
		document.add(table);
		document.close();
		
		
		String subject = "HIS Eligibility Info";
		String body = "HIS Eligibility Info";
		emailUtils.sendEmail(subject, body,appEntity.getEmail() , file);
		updateTrigger(eligData.getCaseNum(), file);
		
		if (file.exists()) {
		    file.delete();
		}
	}
	
	private void updateTrigger(Integer integer, File file) throws Exception {
		CoTriggerEntity byCaseNum = coTriggerEntityRepo.findByCaseNum(integer);
		
		byte[] arr = new byte[(int) file.length()];
		FileInputStream fis = new FileInputStream(file);
		fis.read(arr);
		
		byCaseNum.setCoPdf(arr);
		byCaseNum.setTrgStatus("Completed");
		coTriggerEntityRepo.save(byCaseNum);
		
		fis.close();
	}
	
	public void sample() {
		for(int i=0;i<5;i++) {
			
			System.out.println("Schedular activated "+i);
		}
		
	}

}
