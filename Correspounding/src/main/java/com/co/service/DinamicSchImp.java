package com.co.service;

import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.co.binding.DinamicSchDto;
import com.co.entity.DinamicConfig;
import com.co.entityRepo.DinamicConfigRepo;

@Service
public class DinamicSchImp {

	@Autowired
	private DinamicConfigRepo dinamicConfigRepo;
	@Autowired
	private COServiceImpl cOServiceImpl;
	private final ThreadPoolTaskScheduler taskScheduler=new ThreadPoolTaskScheduler();
	@Autowired
	private TaskRegistry taskRegistry;


	public DinamicSchImp() {
		taskScheduler.initialize(); //this constructor setups executorservice.
	}
	
	public void updateCornExpression(DinamicSchDto dinamicSchDto) {
	    String taskName = dinamicSchDto.getTaskName();
	    
	    DinamicConfig cfg = dinamicConfigRepo
	            .findFirstByTaskName(taskName)
	            .orElseGet(DinamicConfig::new);

	    DinamicConfig dc=new DinamicConfig();
	    dc.setTaskName(dinamicSchDto.getTaskName());
	    dc.setCronExpression(dinamicSchDto.getCronExpression());
	    dinamicConfigRepo.save(dc);

	    rescheduleTask(dinamicSchDto);
	}


	private ScheduledFuture<?> future; // class-level field

	private void rescheduleTask(DinamicSchDto dto) {

	    if (future != null) {
	        future.cancel(false);
	    }

	    Runnable task = taskRegistry.get(dto.getTaskName());
	    if (task == null) {
	        throw new IllegalArgumentException("Task not found: " + dto.getTaskName());
	    }

	    future = taskScheduler.schedule(task, new CronTrigger(dto.getCronExpression()));
	}

	
	
	
	
	
	
	
	
}
