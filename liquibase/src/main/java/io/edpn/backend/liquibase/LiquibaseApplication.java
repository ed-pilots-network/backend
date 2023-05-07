package io.edpn.backend.liquibase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class LiquibaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiquibaseApplication.class, args);
    }

}
