package io.edpn.backend.eddnmessagelistener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class EddnMessageListenerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EddnMessageListenerApplication.class, args);
    }
}
