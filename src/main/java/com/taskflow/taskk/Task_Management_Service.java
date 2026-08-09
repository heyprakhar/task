package com.taskflow.taskk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan
public class Task_Management_Service {

	public static void main(String[] args) {
        log.info("Application started successfully");
		SpringApplication.run(Task_Management_Service.class, args);
	}

}
