package com.dc.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dc.entity.DCIncomeEntity;

public interface DCIncomeRepo extends JpaRepository<DCIncomeEntity, Serializable> {

	public DCIncomeEntity findByCaseNumber(Integer caseNumber);
}
