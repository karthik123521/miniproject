package com.ed.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ed.Entity.EligibilityEntity;
import com.ed.EntityRepo.EligibilityEntityRepo;
import com.ed.FeignClient.FeignClientBE;
import com.ed.FeignClient.FeignClientCO;
import com.ed.FeignClient.FeignClientDob;
import com.ed.FeignClient.FeignClientImpl;
import com.ed.binding.ChildBinding;
import com.ed.binding.CoTriggerBinding;
import com.ed.binding.IncomeBinding;
import com.ed.binding.Summaryfinal;
import com.ed.dto.EligResponse;

@Service
public class EducationServiceImpl implements EducationService {

	//private Logger logger = Logger.getLogger(EducationServiceImpl.class);
	
	public EligibilityEntityRepo eligibilityEntityRepo;
	public FeignClientImpl feignClientImpl;
	public FeignClientDob feignClientDob;
	public FeignClientCO feignClientCO;
	public FeignClientBE feignClientBE;

	public EducationServiceImpl(EligibilityEntityRepo eligibilityEntityRepo, FeignClientImpl feignClientImpl,
			FeignClientDob feignClientDob, FeignClientCO feignClientCO, FeignClientBE feignClientBE) {
		super();
		this.eligibilityEntityRepo = eligibilityEntityRepo;
		this.feignClientImpl = feignClientImpl;
		this.feignClientDob = feignClientDob;
		this.feignClientCO = feignClientCO;
		this.feignClientBE = feignClientBE;
	}

	@Override
	public EligResponse chechEligibility(Integer caseNum) {
		Summaryfinal body = feignClientImpl.getMethodName(caseNum).getBody();
		Integer appId = null;
		String planName = null;
		if (body != null) {
			appId = body.getAppId();
			planName = body.getPlanName();
		}

		Integer age = 0;
		LocalDate body2 = feignClientDob.findDate(appId).getBody();
		if (body2 != null) {
			LocalDate now = LocalDate.now();
			age = Period.between(body2, now).getYears();
		}

		EligResponse chechElig = chechElig(body, appId, planName, age);
		
		EligibilityEntity eligibilityEntity = new EligibilityEntity();
		eligibilityEntity.setPlanName(planName);
		eligibilityEntity.setCaseNum(caseNum);
		BeanUtils.copyProperties(chechElig, eligibilityEntity);
		eligibilityEntityRepo.save(eligibilityEntity);

		CoTriggerBinding coResponse = new CoTriggerBinding();
		coResponse.setCaseNum(caseNum);
		coResponse.setTrgStatus("Pending");
		//feignClientCO.saveTrigger(coResponse).getBody();

		BeanUtils.copyProperties(eligibilityEntity, chechElig);
		
		List<EligResponse> byPlanStatus = eligibilityEntityRepo.findByPlanStatus(true);
		feignClientBE.sendMailToBank(byPlanStatus);

		return chechElig;
	}

	public EligResponse chechElig(Summaryfinal summary, Integer appId, String planName, Integer age) {

		EligResponse eligResponse = new EligResponse();
		eligResponse.setPlanName(planName);

		IncomeBinding income = summary.getIncome();
		if (("SNAP").equals(planName)) {
			Double propertyIncome = income.getPropertyIncome();
			if (propertyIncome <= 50000) {
				eligResponse.setPlanStatus(true);
				eligResponse.setBenefitAmount(10000.00);
			} else {
				eligResponse.setDenialReason("DENIED");
				eligResponse.setPlanStatus(false);
				eligResponse.setBenefitAmount(0.00);
			}
		} else if (("CCAP").equals(planName)) {
			List<ChildBinding> child = summary.getChild();
			Boolean kidCondition = false;
			Boolean ageCondition = true;
			if (!child.isEmpty()) {
				kidCondition = true;
				for (ChildBinding childres : child) {
					Integer childAge = childres.getChildAge();
					if (childAge <= 16) {
						eligResponse.setDenialReason("CHILD AGE IS GREATER THAN 16.");
						eligResponse.setPlanStatus(false);
						kidCondition = false;

						break;
					}

				}
			}

			if (income.getMonthlySalaryIncome() <= 50000 && kidCondition && ageCondition) {
				eligResponse.setPlanStatus(true);
				eligResponse.setBenefitAmount(15000.00);
			} else {
				eligResponse.setPlanStatus(false);
				eligResponse.setDenialReason("No Kid fount");
			}

		} else if (("MEDICARE").equals(planName)) {
			if (age >= 65) {
				eligResponse.setPlanStatus(true);
			} else {
				eligResponse.setPlanStatus(false);
				eligResponse.setDenialReason("Age is not more than 65.");
			}
		} else if (("NJW").equals(planName)) {
			Integer gradYear = summary.getEducation().getGradYear();
			Integer year = LocalDate.now().getYear();
			if (income.getMonthlySalaryIncome() <= 0 && gradYear < year) {
				eligResponse.setPlanStatus(true);
				eligResponse.setBenefitAmount(12000.00);
			} else {
				eligResponse.setPlanStatus(false);
				eligResponse.setDenialReason("rules not satisfied");
			}

		} else if (("MEDICAID").equals(planName)) {
			if (income.getMonthlySalaryIncome() < 5000 && income.getPropertyIncome() == 0
					&& income.getRentIncome() == 0) {
				eligResponse.setPlanStatus(true);
				eligResponse.setBenefitAmount(10000.00);
			} else {
				eligResponse.setPlanStatus(false);
				eligResponse.setDenialReason("rules not satisfied");
			}
		}

		if (eligResponse.getPlanStatus()) {
			eligResponse.setStartDate(LocalDate.now());
			eligResponse.setEndDate(LocalDate.now().plusMonths(12));
		} else {
			eligResponse.setBenefitAmount(0.00);
		}

		return eligResponse;

	}

	@Override
	public EligResponse getEligibility(Integer caseNum) {
		Optional<EligibilityEntity> byId = eligibilityEntityRepo.findById(caseNum);
		if (byId.isPresent()) {
			EligibilityEntity eligibilityEntity = byId.get();
			EligResponse eligResponse = new EligResponse();
			BeanUtils.copyProperties(eligibilityEntity, eligResponse);
			return eligResponse;
		}
		return null;
	}

}
