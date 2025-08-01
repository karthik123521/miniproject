package com.project.login.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.login.Binding.Login;
import com.project.login.Binding.LoginBinding;
import com.project.login.Dto.Dto;
import com.project.login.ServiceRepo.ServiceRepo;

@RestController
public class LoginContoller {

	public ServiceRepo repo;

	public LoginContoller(ServiceRepo repo) {
		super();
		this.repo = repo;
	}

	@PostMapping("/createAccount")
	public ResponseEntity<Boolean> createAccount(@RequestBody LoginBinding login) {
		Boolean dto = repo.create(login);
		if (dto) {
			return new ResponseEntity<Boolean>(dto, HttpStatus.CREATED);
		}
		return new ResponseEntity<Boolean>(dto, HttpStatus.BAD_REQUEST);

	}
	

	@PutMapping("/setPass")
	public ResponseEntity<String> activateUserAccount(@RequestBody Dto dto) {
		String setPass = repo.activateUser(dto);
		return new ResponseEntity<String>(setPass, HttpStatus.ACCEPTED);
	}

	@PutMapping("/forgotPassword")
	public ResponseEntity<String> forget(@RequestBody Dto dto) {
		String forgetPass = repo.forgetPass(dto);
		return new ResponseEntity<String>(forgetPass, HttpStatus.CREATED);
	}

	@GetMapping("/Login")
	public ResponseEntity<String> login(@RequestBody Login log) {
		String login = repo.Login(log);
		return new ResponseEntity<String>(login, HttpStatus.OK);
	}
	@GetMapping("/getById/{id}")
	public ResponseEntity<LoginBinding> getById(@PathVariable Integer id){
		LoginBinding byId = repo.getById(id);
		return new ResponseEntity<LoginBinding>(byId,HttpStatus.FOUND);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<LoginBinding>> get(){
		List<LoginBinding> all = repo.getAll();
		return new ResponseEntity<List<LoginBinding>>(all,HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Integer id){
		String deleteById = repo.deleteById(id);
		return new ResponseEntity<String>(deleteById,HttpStatus.GONE);
	}
}
