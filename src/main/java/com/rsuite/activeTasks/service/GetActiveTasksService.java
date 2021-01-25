package com.rsuite.activeTasks.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsuite.activeTasks.model.ActiveTasks;

@Service
public class GetActiveTasksService {
	
	ObjectMapper mapper = new ObjectMapper();
	Map<String, Object> jsonActiveTask = new HashMap<String, Object>();
	List<ActiveTasks> activeTaskList = new ArrayList<>();
	
	private final JdbcTemplate jdbcTemplate;
	 
    public GetActiveTasksService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

	
	public String getAllActiveTasks() throws JsonProcessingException {
		String query = "SELECT * FROM v_activetasks";
		activeTaskList =  jdbcTemplate.query(query, new RowMapper<ActiveTasks>() {
			@Override
			public ActiveTasks mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
				ActiveTasks activeTasks = new ActiveTasks();
				activeTasks.setTaskId(resultSet.getString("taskId"));
				activeTasks.setDescription(resultSet.getString("description"));
				activeTasks.setStartDate(resultSet.getString("startDate"));
				activeTasks.setRefxml(resultSet.getString("refxml"));
				activeTasks.setType(resultSet.getString("type"));
				return activeTasks;
			}
		});
		jsonActiveTask.put("activeTasks", activeTaskList);
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonActiveTask);
	}
	
	public String getActiveTasksByConfigurationId(String configurationId) throws JsonProcessingException {
		String query = "SELECT * FROM v_activetasks where refxml=" + "'" +configurationId+ "'";
		activeTaskList = jdbcTemplate.query(query, new RowMapper<ActiveTasks>() {
			@Override
			public ActiveTasks mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
				ActiveTasks activeTasks = new ActiveTasks();
				activeTasks.setTaskId(resultSet.getString("taskId"));
				activeTasks.setDescription(resultSet.getString("description"));
				activeTasks.setStartDate(resultSet.getString("startDate"));
				activeTasks.setRefxml(resultSet.getString("refxml"));
				activeTasks.setType(resultSet.getString("type"));
				return activeTasks;
			}
		});
		jsonActiveTask.put("activeTasks", activeTaskList);
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonActiveTask);
	}
}