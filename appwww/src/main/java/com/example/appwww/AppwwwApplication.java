package com.example.appwww;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AppwwwApplication {
	public static void main(String[] args) {
		SpringApplication.run(AppwwwApplication.class, args);
	}
}
