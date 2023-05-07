package io.edpn.backend.messagelistener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class MessageListenerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessageListenerApplication.class, args);
    }
}
