package com.rsuite.activeTasks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rsuite.activeTasks.service.GetActiveTasksService;

@RestController
@RequestMapping(path = "/", produces = "application/json")
public class GetActiveTaskController {

	@Autowired
	GetActiveTasksService getActiveTasksService;
	 
	@GetMapping(path="/getActiveTasks")
	public String getActiveTasks() throws JsonProcessingException {
		return getActiveTasksService.getAllActiveTasks();
		
	}
	
	@GetMapping(path="/getActiveTasks/{configurationID}")
	public String getActiveTasksByConfigurationId(@PathVariable String configurationID) throws JsonProcessingException {
		return getActiveTasksService.getActiveTasksByConfigurationId(configurationID);
		
	}
}
