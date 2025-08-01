package com.ar.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ar.binding.CitizenApp;
import com.ar.entity.CitizenApiEntity;
import com.ar.repository.CitizenAppRepository;

@Service
public class ArServiceImpl implements ArService {

	@Autowired
	private CitizenAppRepository appRepo;

	@Override
	public Integer createApplication(CitizenApp app) {
		String endpointUrl = "http://65.2.31.198:8091/getState/{ssn}";

		/*
		 * RestTemplate rt = new RestTemplate(); ResponseEntity<String> responseEntity =
		 * rt.getForEntity(endpointUrl, String.class, app.getSsn()); String stateName =
		 * responseEntity.getBody();
		 */

		String stateName = WebClient.create() // Creates webclient instance
				.get() // Represents Get request
				.uri(endpointUrl, app.getSsn()) // Represents url to send request
				.retrieve() // To retrieve response
				.bodyToMono(String.class) // To mention the response type
				.block(); // To make synchronous call

		if ("New Jersey".equals(stateName)) {
			// Create application;
			CitizenApiEntity entity = new CitizenApiEntity();
			BeanUtils.copyProperties(app, entity);

			entity.setStateName(stateName);
			CitizenApiEntity citizenApiEntity = appRepo.save(entity);

			return citizenApiEntity.getAppId();
		}
		return 0;
	}

	@Override
	public Boolean checkAppId(Integer appId) {
		Optional<CitizenApiEntity> byId = appRepo.findById(appId);
		if (byId.isPresent()) {
			return true;
		}
		return false;
	}

	@Override
	public LocalDate findDob(Integer date) {
		LocalDate dobById = appRepo.findDobById(date);

		return dobById;
	}

	@Override
	public CitizenApp findId(Integer id) {
	    Optional<CitizenApiEntity> byId = appRepo.findById(id);
	    if (byId.isPresent()) {
	        CitizenApiEntity citizenApiEntity = byId.get();
	        CitizenApp citizenApp = new CitizenApp();
	        BeanUtils.copyProperties(citizenApiEntity, citizenApp);
	        
	        return citizenApp;
	    }
	    return null;
	}


}
