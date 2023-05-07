package io.edpn.backend.messageprocessor.commodityv3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class CommodityV3ProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommodityV3ProcessorApplication.class, args);
    }

}
