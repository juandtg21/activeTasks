package com.rsuite.activeTasks.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsuite.activeTasks.model.ActiveTasks;

@Service
public class GetActiveTasksService {
	
	ObjectMapper mapper = new ObjectMapper();
	Map<String, Object> jsonActiveTask = new HashMap<String, Object>();
	List<ActiveTasks> activeTaskList = new ArrayList<>();
	
	@Value("${mysql.datasource.url}")
	private String mysqlUrl;
	
	@Value("${mysql.datasource.driverClassName}")
	private String mysqlDriver;
	
	@Value("${mysql.datasource.username}")
	private String mysqlUser;
	
	@Value("${mysql.datasource.password}")
	private String mysqlPassword;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Bean
	public DataSource getDatasource() {
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setDriverClassName(mysqlDriver);
		datasource.setUrl(mysqlUrl);
		datasource.setUsername(mysqlUser);
		datasource.setPassword(mysqlPassword);
		return datasource;
	}

	@Bean
	public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
		// return jdbcTemplate;
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