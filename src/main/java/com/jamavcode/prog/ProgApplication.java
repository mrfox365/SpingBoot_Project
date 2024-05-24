package com.jamavcode.prog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.jamavcode.prog.repository")
public class ProgApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgApplication.class, args);
	}

}
