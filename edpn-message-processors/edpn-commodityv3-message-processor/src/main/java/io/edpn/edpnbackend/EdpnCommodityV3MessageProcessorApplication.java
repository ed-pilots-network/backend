package io.edpn.edpnbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class EdpnCommodityV3MessageProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdpnCommodityV3MessageProcessorApplication.class, args);
    }

}
