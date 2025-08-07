package com.project.cat.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.cat.Binding.CaseWorkerBinding;
import com.project.cat.Binding.Login;
import com.project.cat.Entity.CaseWorker;
import com.project.cat.Service.CaseWorkerService;

@RestController
public class CaseWorkerController {
	@Autowired
	private CaseWorkerService caseWorkerService;
	
	@GetMapping("/caselist")
	public ResponseEntity<List<CaseWorker>> getCaseUsersList(){
		List<CaseWorker> allUsers = caseWorkerService.getAllUsers();
		return new ResponseEntity<>(allUsers,HttpStatus.OK);
		
	}
	
	@PostMapping("/savecaseworker")
	public ResponseEntity<String> saveCaseWorker(@RequestBody CaseWorkerBinding caseWorker ){
		String saveCaseworker = caseWorkerService.saveCaseworker(caseWorker);
		return new ResponseEntity<String>(saveCaseworker, HttpStatus.OK);
	}
	
	@GetMapping("/caseid/{userId}")
	public ResponseEntity<CaseWorker> getUserById(@PathVariable String userId) {
		CaseWorker userById = caseWorkerService.getUserById(userId);
		return new ResponseEntity<>(userById, HttpStatus.OK);
	}

	@DeleteMapping("/caseid/{userId}")
	public ResponseEntity<String> deleteUserById(@PathVariable String userId) {
		boolean isDeleted = caseWorkerService.deleteUserById(userId);

		if (isDeleted) {
			return new ResponseEntity<String>("User Deleted successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("User Delete Failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/status/{userId}/{status}")
	public ResponseEntity<String> statusChange(@PathVariable String userId, @PathVariable Boolean status) {
		boolean isChanged = caseWorkerService.changeAccountStatus(userId, status);
		if (isChanged) {
			return new ResponseEntity<>("Status Changed", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Failed to Change", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Login login) {
		String loginStatus = caseWorkerService.login(login);
		return new ResponseEntity<String>(loginStatus, HttpStatus.OK);
	}
	
	@GetMapping("forgetpassword/{email}")
	public ResponseEntity<String> forgetPassword(@PathVariable String email){
		String forgotPwd = caseWorkerService.forgotPwd(email);
		return new ResponseEntity<String>(forgotPwd, HttpStatus.OK);
	}

	@PutMapping("/update/{userId}")
	public ResponseEntity<String> update(@PathVariable String userId,@RequestBody CaseWorkerBinding caseWorker ){
		 String updateCaseWorker = caseWorkerService.updateCaseWorker(userId, caseWorker);
		return new ResponseEntity<String>(updateCaseWorker, HttpStatus.OK);
	}
	

}
