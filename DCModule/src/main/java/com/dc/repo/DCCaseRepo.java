package com.dc.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dc.entity.DCCaseEntity;

public interface DCCaseRepo extends JpaRepository<DCCaseEntity, Serializable> {

	public DCCaseEntity findByAppId(Integer appId);
	
}
