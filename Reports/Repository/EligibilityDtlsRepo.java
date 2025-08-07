package com.Reports.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Reports.Entity.EligibilityDtls;


public interface EligibilityDtlsRepo extends JpaRepository<EligibilityDtls, Integer>{
	
	

	@Query(value="select distinct plan_Name from Eligibility_Details", nativeQuery=true)
	List<String> findPlanNames();
	
	@Query(value="select distinct PLAN_STATUS from Eligibility_Details", nativeQuery=true)
	List<String> findPlanStatuses();

}
