package com.project.cat.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.project.cat.Binding.PlanBinding;
import com.project.cat.Binding.PlanMasterBinding;
import com.project.cat.Entity.PlanCatagoryEntity;
import com.project.cat.Entity.PlanMasterEntity;
import com.project.cat.EntityRepo.PlanCharRepo;
import com.project.cat.EntityRepo.PlanMasterRepo;
import com.project.cat.constants.AppConstants;

@Service
public class PlanMasterServiceImpl implements PlanService {

	private PlanCharRepo planChar;

	private PlanMasterRepo planMaster;

	public PlanMasterServiceImpl(PlanCharRepo planChar, PlanMasterRepo planMaster) {
		this.planChar = planChar;
		this.planMaster = planMaster;
	}

	@Override
	public Map<Integer, String> getPlanCategories() {

		List<PlanCatagoryEntity> Cats = planChar.findAll();
		Map<Integer, String> ActCats = new HashMap<>();
		Cats.forEach(c -> {
			if (c.getActiveSwitch()) {
				ActCats.put(c.getCatId(), c.getCatName());
			}
		});

		return ActCats;
	}

	@Override
	public boolean savePlan(PlanMasterEntity plan) {

		PlanMasterEntity save = planMaster.save(plan);
		return save != null;
	}

	@Override
	public Map<Integer, String> getAllPlans() {
	    List<PlanMasterEntity> all = planMaster.findAll();
	    Map<Integer, String> map = new HashMap<>();

	    for (PlanMasterEntity entity : all) {
	        PlanBinding plan = new PlanBinding();
	        BeanUtils.copyProperties(entity, plan);

	        Integer id = plan.getPlanCatId();
	        String name = plan.getPlanName();

	        map.put(id, name);
	    }

	    return map;
	}


	@Override
	public PlanMasterEntity getPlanById(Integer planId) {
		Optional<PlanMasterEntity> byId = planMaster.findById(planId);
		if (byId.isEmpty()) {
			return null;
		}
		return byId.get();
	}

	@Override
	public String updatePlan(PlanMasterEntity plan) {

		Optional<PlanMasterEntity> getPlan = planMaster.findById(plan.getPlanId());
		if (getPlan.isPresent()) {
			PlanMasterEntity pEntity = getPlan.get();

			if (pEntity != null) {
				pEntity.setPlanName(plan.getPlanName());
				pEntity.setUpdateDate(plan.getUpdateDate());
				pEntity.setUpdatedBy(plan.getUpdatedBy());
				pEntity.setPlanEndDate(plan.getPlanEndDate());
				pEntity.setPlanStartDate(plan.getPlanStartDate());

				PlanMasterEntity pme = new PlanMasterEntity();
				BeanUtils.copyProperties(pEntity, pme);
				planMaster.save(pme);

				return AppConstants.PLAN_UPDATE_SUCC;
			}

		}
		return AppConstants.PLAN_UPDATE_FAIL;
	}

	@Override
	public boolean deletePlanById(PlanMasterEntity plan) {

		Optional<PlanMasterEntity> byId = planMaster.findById(plan.getPlanId());
		if (byId.isPresent()) {
			PlanMasterEntity planMasterEntity = byId.get();
			planMaster.deleteById(planMasterEntity.getPlanId());
			return true;
		}

		return false;
	}

	@Override
	public boolean planStatusChange(Integer planId, Boolean activeSw) {

		Optional<PlanMasterEntity> byId = planMaster.findById(planId);
		if (byId.isPresent()) {
			PlanMasterEntity planMasterEntity = byId.get();
			planMasterEntity.setActiveSwitch(activeSw);
			planMaster.save(planMasterEntity);

			return true;
		}

		return false;
	}

	@Override
	public PlanMasterEntity postPlan(PlanMasterBinding pm) {

		// List<PlanCatagoryEntity> categories = planChar.findAll();

//		Map<Integer, String> ac = categories.stream().filter(c -> (c.getActiveSwitch()))
//				.filter(c -> c.getCatId() != null && c.getCatName() != null)
//				.collect(Collectors.toMap
//						(PlanCatagoryEntity::getCatId, PlanCatagoryEntity::getCatName,(name1, name2) -> name1));
//	    String categoryName = activeCategories.get(pm.getPlanCatId());
//
//	    if (categoryName == null) {
//	        throw new IllegalArgumentException("Invalid category ID: " + pm.getPlanCatId());
//	    }

		PlanMasterEntity pb = new PlanMasterEntity();
		pb.setActiveSwitch(pm.getActiveSwitch());
		pb.setCreateDate(pm.getCreateDate());
		pb.setCreatedBy(pm.getCreatedBy());
		pb.setPlanCatId(pm.getPlanCatId());
		// pb.setPlancatName(ac.get(pm.getPlanCatId()));
		pb.setPlanEndDate(pm.getPlanEndDate());
		pb.setPlanName(pm.getPlanName());
		pb.setPlanStartDate(pm.getPlanStartDate());

		PlanMasterEntity pme = new PlanMasterEntity();
		BeanUtils.copyProperties(pb, pme);
		planMaster.save(pme);

		return pme;

	}

}
