package com.ed.EntityRepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ed.Entity.EligibilityEntity;
import com.ed.dto.EligResponse;

import feign.Param;

@Repository
public interface EligibilityEntityRepo extends JpaRepository<EligibilityEntity, Integer>{

	@Query("SELECT new com.ed.dto.EligResponse(e.caseNum, e.planName, e.planStatus, e.benefitAmount, e.startDate, e.endDate, e.denialReason) FROM EligibilityEntity e WHERE e.planStatus = :status")
	List<EligResponse> findByPlanStatus(@Param("status") Boolean status);



}
