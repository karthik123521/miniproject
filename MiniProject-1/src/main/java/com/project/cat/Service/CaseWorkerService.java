package com.project.cat.Service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.project.cat.Binding.CaseWorkerBinding;
import com.project.cat.Binding.Login;
import com.project.cat.Entity.CaseWorker;


public interface CaseWorkerService {

	public String saveCaseworker(CaseWorkerBinding caseWorkerBinding);

	public List<CaseWorker> getAllUsers();

	public CaseWorker getUserById(String userId);

	public boolean deleteUserById(String userId);

	public String login(Login login);

	public String forgotPwd(String email);

	public boolean changeAccountStatus(String userId, Boolean accStatus);
	
	public String updateCaseWorker(String userId,CaseWorkerBinding caseWorkerBinding);
}
