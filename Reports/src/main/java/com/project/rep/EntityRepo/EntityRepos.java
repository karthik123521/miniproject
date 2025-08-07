package com.project.rep.EntityRepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.rep.Entity.ReportEntity;

@Repository
public interface EntityRepos extends JpaRepository<ReportEntity, Integer>{
	

	@Query(value = "SELECT DISTINCT plan_name FROM report_entity", nativeQuery = true)
	List<String> findAllPlanName();

	@Query(value = "SELECT DISTINCT plan_status FROM report_entity", nativeQuery = true)
	List<String> findAllPlanStatus();

	


}
