package com.allibilli.potluck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.allibilli")
public class PotluckApplication {

	public static void main(String[] args) {
		SpringApplication.run(PotluckApplication.class, args);
	}
}
