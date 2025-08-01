package com.dc.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dc.entity.DCChildrenEntity;

public interface DCChildrenRepo extends JpaRepository<DCChildrenEntity, Serializable> {

	public List<DCChildrenEntity> findByCaseNumber(Integer caseNumber);
}
