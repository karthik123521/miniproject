package com.dc.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dc.entity.DCEducationEntity;

public interface DCEducationRepo extends JpaRepository<DCEducationEntity, Serializable> {

	public DCEducationEntity findByCaseNumber(Integer caseNumber);
}
