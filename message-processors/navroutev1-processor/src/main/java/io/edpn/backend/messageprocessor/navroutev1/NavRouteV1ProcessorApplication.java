package io.edpn.backend.messageprocessor.navroutev1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class NavRouteV1ProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(NavRouteV1ProcessorApplication.class, args);
    }
}
