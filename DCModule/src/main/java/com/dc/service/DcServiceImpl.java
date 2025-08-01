package com.dc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dc.binding.ChildBinding;
import com.dc.binding.ChildRequestBinding;
import com.dc.binding.DCCaseBinding;
import com.dc.binding.EducationBinding;
import com.dc.binding.IncomeBinding;
import com.dc.binding.PlanSelectionBinding;
import com.dc.binding.Summaryfinal;
import com.dc.entity.DCCaseEntity;
import com.dc.entity.DCChildrenEntity;
import com.dc.entity.DCEducationEntity;
import com.dc.entity.DCIncomeEntity;
import com.dc.feignClient.FeignClientPlans;
import com.dc.feignClient.FeignClientUser;
import com.dc.repo.DCCaseRepo;
import com.dc.repo.DCChildrenRepo;
import com.dc.repo.DCEducationRepo;
import com.dc.repo.DCIncomeRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DcServiceImpl implements DcService {

	public FeignClientUser feignClientUser;
	public FeignClientPlans feignClientPlans;
	private DCChildrenRepo dCChildrenRepo;
	private DCEducationRepo dCEducationRepo;
	private DCIncomeRepo dCIncomeRepo;
	private DCCaseRepo dCCaseRepo;

	public DcServiceImpl(FeignClientUser feignClientUser, FeignClientPlans feignClientPlans,
			DCChildrenRepo dCChildrenRepo, DCEducationRepo dCEducationRepo, DCIncomeRepo dCIncomeRepo,
			DCCaseRepo dCCaseRepo) {
		super();
		this.feignClientUser = feignClientUser;
		this.feignClientPlans = feignClientPlans;
		this.dCChildrenRepo = dCChildrenRepo;
		this.dCEducationRepo = dCEducationRepo;
		this.dCIncomeRepo = dCIncomeRepo;
		this.dCCaseRepo = dCCaseRepo;
	}

	@Override
	public Integer getAppId(Integer id) {
		ResponseEntity<Boolean> response = feignClientUser.searchAppId(id);
		Boolean exists = response.getBody();

		if (Boolean.TRUE.equals(exists) && dCCaseRepo.findByAppId(id) == null) {
			DCCaseEntity dc = new DCCaseEntity();
			dc.setAppId(id);
			DCCaseEntity save = dCCaseRepo.save(dc);

			return save.getCaseNum();
		}
		if (dCCaseRepo.findByAppId(id) != null) {
			DCCaseEntity byAppId = dCCaseRepo.findByAppId(id);
			return byAppId.getCaseNum();
		}
		return null;
	}

	@Override
	public Map<Integer, String> getPlans() {
		return feignClientPlans.findAllPlans().getBody();
	}

	@Override
	public Integer saveIncomeData(IncomeBinding incomeBinding) {
		Optional<DCCaseEntity> byId = dCCaseRepo.findById(incomeBinding.getCaseNumber());
		if (byId.isPresent()) {
			DCIncomeEntity incomeEntity = new DCIncomeEntity();
			BeanUtils.copyProperties(incomeBinding, incomeEntity);
			DCIncomeEntity save = dCIncomeRepo.save(incomeEntity);
			return save.getCaseNumber();
		}
		return null;
	}

	@Override
	public Integer saveEducation(EducationBinding educationBinding) {
		DCIncomeEntity byCaseNumber = dCIncomeRepo.findByCaseNumber(educationBinding.getCaseNumber());
		if (byCaseNumber!=null) {
			DCEducationEntity dc = new DCEducationEntity();
			BeanUtils.copyProperties(educationBinding, dc);
			DCEducationEntity save = dCEducationRepo.save(dc);
			return save.getCaseNumber();

		}
		return null;
	}

	@Override
	public ChildRequestBinding saveChildrenData(ChildRequestBinding childRequestBinding) {
		DCEducationEntity byCaseNumber = dCEducationRepo.findByCaseNumber(childRequestBinding.getCaseNumber());

		if (byCaseNumber!=null && childRequestBinding.getChilds() != null) {
			for (ChildBinding child : childRequestBinding.getChilds()) {
				DCChildrenEntity dc = new DCChildrenEntity();
				dc.setCaseNumber(childRequestBinding.getCaseNumber());
				dc.setChildName(child.getChildName());
				dc.setChildAge(child.getChildAge());
				dc.setSsn(child.getSsn());

				dCChildrenRepo.save(dc);
				return childRequestBinding;
			}
		}

		return null;
	}

	@Override
	public Integer savePlanSelection(PlanSelectionBinding planSelectionBinding) {
		Optional<DCCaseEntity> byId = dCCaseRepo.findById(planSelectionBinding.getCaseNum());
		if (byId.isPresent()) {
			DCCaseEntity dcCaseEntity = byId.get();
			dcCaseEntity.setPlanId(planSelectionBinding.getPlanId());
			dCCaseRepo.save(dcCaseEntity);
			return dcCaseEntity.getCaseNum();
		}
		return null;
	}

	@Override
	public Summaryfinal getSummary(Integer caseNum) {
		String name = "";
		Optional<DCCaseEntity> byId = dCCaseRepo.findById(caseNum);
		if (byId.isPresent()) {
			Map<Integer, String> planesNames = getPlans();
			String string = planesNames.get(byId.get().getPlanId());
			name = string;
		}

		Summaryfinal summary = new Summaryfinal();
		summary.setPlanName(name);
		summary.setAppId(byId.get().getAppId());

		DCEducationEntity educationEntity = dCEducationRepo.findByCaseNumber(caseNum);
		EducationBinding educationBinding = new EducationBinding();
		if (educationEntity != null) {
			BeanUtils.copyProperties(educationEntity, educationBinding);
			summary.setEducation(educationBinding);
		}

		DCIncomeEntity incomeEntity = dCIncomeRepo.findByCaseNumber(caseNum);
		IncomeBinding incomeBinding = new IncomeBinding();
		if (incomeEntity != null) {
			BeanUtils.copyProperties(incomeEntity, incomeBinding);
			summary.setIncome(incomeBinding);
		}

		List<DCChildrenEntity> byCaseNumber = dCChildrenRepo.findByCaseNumber(caseNum);
		List<ChildBinding> childs = new ArrayList<ChildBinding>();
		for (DCChildrenEntity ce : byCaseNumber) {
			ChildBinding child = new ChildBinding();
			BeanUtils.copyProperties(ce, child);
			childs.add(child);
		}

		summary.setChild(childs);

		return summary;
	}

	@Override
	public DCCaseBinding getcase(Integer id) {
	    Optional<DCCaseEntity> byId = dCCaseRepo.findById(id);
	    
	    if (byId.isPresent()) {
	        DCCaseEntity dcCaseEntity = byId.get();
	        DCCaseBinding dc = new DCCaseBinding();
	        BeanUtils.copyProperties(dcCaseEntity, dc);
	        return dc;
	    } else {
	        throw new EntityNotFoundException("DCCaseEntity not found with id: " + id);
	    }
	}


}
