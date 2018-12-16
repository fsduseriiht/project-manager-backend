package com.cts.fsd.projectmanager;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Amitabha Das [420652]
 *
 */
@SpringBootApplication
public class ApplicationMain extends ServletInitializer {
	public static void main(String[] args) throws IOException {
		
//		set application configurations required to run the spring boot application
		setSpringBootApplicationConfiguration();
		
//		actual method call inside the main() method to run Spring Boot Application 
		SpringApplication.run(ApplicationMain.class, args);
	}
	
	
	private static void setSpringBootApplicationConfiguration() {
		
		System.setProperty("server.servlet.context-path", "/projectmanagerbackend");
//		System.setProperty("server.port", "8080");
		
//		spring.datasource properties
//		Properties related to the MySQL Database
		System.setProperty("spring.datasource.url", "jdbc:mysql://localhost:3306/fsd_project_manager?createDatabaseIfNotExist=true");
		System.setProperty("spring.datasource.username", "root");
		System.setProperty("spring.datasource.password", "admin");
		System.setProperty("spring.datasource.driverClassName", "com.mysql.jdbc.Driver");
		System.setProperty("spring.datasource.initialize", "true");
//		Properties related to the H2 Database
		/*System.setProperty("spring.datasource.url", "jdbc:h2:file:~\\tmp\\fsd_project_manager");
		System.setProperty("spring.datasource.name", "fsd_project_manager");
		System.setProperty("spring.datasource.username", "SA");
		System.setProperty("spring.datasource.password", "");
		System.setProperty("spring.datasource.driverClassName", "org.h2.Driver");
		System.setProperty("spring.datasource.initialize", "true");*/
		
		
//		spring.jpa properties
		System.setProperty("spring.jpa.generate-ddl", "true");
		System.setProperty("spring.jpa.hibernate.ddl-auto", "update");
//		System.setProperty("spring.jpa.database-platform", "org.hibernate.dialect.MySQL5Dialect");
//		System.setProperty("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		System.setProperty("spring.jpa.database-platform", "org.hibernate.dialect.MySQLDialect");
		System.setProperty("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//		System.setProperty("spring.jpa.database-platform", "org.hibernate.dialect.H2Dialect");
//		System.setProperty("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		System.setProperty("spring.jpa.show-sql", "true");
		
//		spring.h2 properties
		System.setProperty("spring.h2.console.enabled", "true");
		System.setProperty("spring.h2.console.path", "/h2-console");
	}
	
	
}
