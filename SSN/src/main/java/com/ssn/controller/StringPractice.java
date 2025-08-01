package com.ssn.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class StringPractice {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService service = Executors.newFixedThreadPool(1);

        Callable<String> task = () -> {
            for (int i = 0; i <= 3; i++) {
                System.out.println("Running: " + i);
                Thread.sleep(2000);
            }
            return "Task Completed";
        };

        Future<String> result = service.submit(task);

        System.out.println("Result: " + result.get());

        service.shutdown();
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
		
	}
}
