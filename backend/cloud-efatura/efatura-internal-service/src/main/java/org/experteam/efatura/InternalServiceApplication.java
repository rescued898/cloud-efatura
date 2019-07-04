package org.experteam.efatura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class InternalServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InternalServiceApplication.class, args);
    }
}
