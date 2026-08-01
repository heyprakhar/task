package com.taskflow.taskk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Task_Management_Service {

	public static void main(String[] args) {
        log.info("Application started successfully without spring security");
		SpringApplication.run(Task_Management_Service.class, args);
	}

}
