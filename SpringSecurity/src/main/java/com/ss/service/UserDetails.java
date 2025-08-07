package com.ss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ss.Model.UserPrinciple;
import com.ss.repo.User;
import com.ss.repo.UserRepo;

@Service
public class UserDetails implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		User byUserName = userRepo.findByUserName(username);
		if(byUserName == null) {
			throw new UsernameNotFoundException("User Not Found");
		}
		
		return new UserPrinciple(byUserName);
	}

}
