package com.project.login.ServiceRepo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.project.login.Binding.Login;
import com.project.login.Binding.LoginBinding;
import com.project.login.Dto.Dto;

@Repository
public interface ServiceRepo {
	
	public Boolean create(LoginBinding login);//creating acc ->tempPasss
	
	public String activateUser(Dto dto); //to set a new pass
	
	public String forgetPass(Dto dto); //
	
	public String Login(Login login); 
	
	public LoginBinding getById(Integer id);
	
	public List<LoginBinding> getAll();
	
	public String deleteById(Integer id);
}
