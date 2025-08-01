package com.project.cat.EntityRepo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.cat.Entity.CaseWorker;

@Repository
public interface CaseWorkerRepo extends JpaRepository<CaseWorker,String>{

	public Optional<CaseWorker> findByEmail(String email);
	public Optional<CaseWorker> findByEmailAndPassword(String email,String password);

}
