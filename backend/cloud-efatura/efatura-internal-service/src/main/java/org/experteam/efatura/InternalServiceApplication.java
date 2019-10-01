package org.experteam.efatura;

import com.oembedler.moon.graphql.boot.GraphQLWebsocketAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = {
        GraphQLWebsocketAutoConfiguration.class
})
public class InternalServiceApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(InternalServiceApplication.class, args);
    }
}
