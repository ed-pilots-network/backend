package io.edpn.backend.exploration.adapter.config;

import io.edpn.backend.exploration.adapter.kafka.dto.SystemCoordinatesResponseMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("ExplorationKafkaMapperConfig")
public class KafkaMapperConfig {


    @Bean(name = "explorationSystemCoordinatesResponseMapper")
    public SystemCoordinatesResponseMapper systemCoordinatesResponseMapper() {
        return new SystemCoordinatesResponseMapper();
    }
}
