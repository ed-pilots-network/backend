package io.edpn.backend.eddnrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class EddnRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(EddnRestApplication.class, args);
    }
}
