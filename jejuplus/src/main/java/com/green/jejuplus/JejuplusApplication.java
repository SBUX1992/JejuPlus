package com.green.jejuplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JejuplusApplication {

	public static void main(String[] args) {
		SpringApplication.run(JejuplusApplication.class, args);
	}

}
