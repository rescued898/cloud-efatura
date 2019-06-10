package org.experteam.efatura;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication//(exclude={DataSourceAutoConfiguration.class})
public class EfaturaCommonsApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(EfaturaCommonsApplication.class, args);

        ctx.close();
    }
}
