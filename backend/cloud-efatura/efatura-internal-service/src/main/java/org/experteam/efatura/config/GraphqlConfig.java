package org.experteam.efatura.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories({"org.experteam.efatura.dao"})
public class GraphqlConfig {
}
