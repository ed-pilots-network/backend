package io.eddb.eddb2backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class Eddb2BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(Eddb2BackendApplication.class, args);
    }

}
