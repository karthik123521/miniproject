package com.co.entityRepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.co.entity.CoTriggerEntity;

public interface CoTriggerEntityRepo extends JpaRepository<CoTriggerEntity, Integer> {

	public List<CoTriggerEntity> findByTrgStatus(String staus);

	public CoTriggerEntity findByCaseNum(Integer caseNum);
}
