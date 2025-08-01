package com.co.entityRepo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.co.entity.DinamicConfig;

@Repository
public interface DinamicConfigRepo extends JpaRepository<DinamicConfig, Integer>{

	Optional<DinamicConfig> findFirstByTaskName(String taskName);
}
