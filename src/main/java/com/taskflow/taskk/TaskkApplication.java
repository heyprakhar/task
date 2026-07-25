package com.taskflow.taskk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class TaskkApplication {

	public static void main(String[] args) {
        log.info("Application started successfully");
		SpringApplication.run(TaskkApplication.class, args);
	}

}
