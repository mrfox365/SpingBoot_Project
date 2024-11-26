package com.jamavcode.prog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main class for starting the Prog application.
 * This class uses Spring Boot to bootstrap the application.
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.jamavcode.prog.repository")
public class ProgApplication {

    /**
     * Main method to start the Prog application.
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(ProgApplication.class, args);
    }

}
