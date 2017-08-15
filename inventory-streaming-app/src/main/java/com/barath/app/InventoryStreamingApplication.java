package com.barath.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InventoryStreamingApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryStreamingApplication.class, args);
	}
}
