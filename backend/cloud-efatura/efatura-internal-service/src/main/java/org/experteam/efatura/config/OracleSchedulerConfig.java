package org.experteam.efatura.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories({"org.experteam.efatura.mongodb"})
public class OracleSchedulerConfig {
}
