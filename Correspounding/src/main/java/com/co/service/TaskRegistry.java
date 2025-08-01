package com.co.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class TaskRegistry {
	

    private final Map<String, Runnable> taskMap = new HashMap<>();

    public TaskRegistry(COServiceImpl coServiceImpl) {
        taskMap.put("sample", coServiceImpl::sample);
        taskMap.put("processPendingTriggers", coServiceImpl::processPendingTriggers);
    }

    public Runnable get(String name) {
        return taskMap.get(name);
    }

    public Set<String> getTaskNames() {
        return taskMap.keySet();
    }
}

