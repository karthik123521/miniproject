package com.project.cat.EntityRepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.cat.Entity.PlanCatagoryEntity;


public interface PlanCharRepo extends JpaRepository<PlanCatagoryEntity, Integer>{

}
