package com.rsuite.activeTasks.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
 
@Configuration
public class ApplicationContext {
     
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
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);        
    }
     
    @Bean
    DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }   
 
}