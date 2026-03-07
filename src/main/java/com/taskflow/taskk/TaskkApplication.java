package com.taskflow.taskk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class TaskkApplication {

	public static void main(String[] args) {
        log.info("Application started successfully");
		SpringApplication.run(TaskkApplication.class, args);
	}

}
