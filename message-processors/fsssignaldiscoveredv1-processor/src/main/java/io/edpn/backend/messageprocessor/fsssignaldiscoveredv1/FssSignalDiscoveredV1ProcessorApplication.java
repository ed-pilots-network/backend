package io.edpn.backend.messageprocessor.fsssignaldiscoveredv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class FssSignalDiscoveredV1ProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(FssSignalDiscoveredV1ProcessorApplication.class, args);
    }
}
