package com.ar.service;

import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import com.ar.binding.CitizenApp;

@Service
public class ArServiceImplTest {

	public ArServiceImpl arServiceImpl;
	@Test
	public void firstTest() {
		System.out.println("First test");
		CitizenApp C=new CitizenApp();
		arServiceImpl.checkAppId(C.hashCode());
	}
}
