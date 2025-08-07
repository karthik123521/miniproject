package com.project.cat.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.project.cat.Binding.PlanCatagoryBinding;
import com.project.cat.Entity.PlanCatagoryEntity;
import com.project.cat.EntityRepo.PlanCharRepo;
import com.project.cat.Properties.AppProperties;
import com.project.cat.constants.AppConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlanCatServiceImpl implements PlanCatService {

	private PlanCharRepo planChar;
	private AppProperties appPro;

	public PlanCatServiceImpl(PlanCharRepo planChar, AppProperties appPro) {
		this.planChar = planChar;
		this.appPro = appPro;
	}
	//private static final Logger logger = LoggerFactory.getLogger(PlanCatService.class);

	@Override
	public String postPlanCat(PlanCatagoryBinding planEntity) {

		planEntity.getCatName();
		planEntity.getCreateDate();
		planEntity.getCreatedBy();
		planEntity.getActiveSwitch();

		PlanCatagoryEntity plan=new PlanCatagoryEntity();
		BeanUtils.copyProperties(planEntity, plan);
		planChar.save(plan);
		
		log.info("Cat working properly");
		
		return appPro.getMessages().get(AppConstants.NEW_CAT_ADDED);
		//return AppConstants.NEW_CAT_ADDED;
	}

	

}
