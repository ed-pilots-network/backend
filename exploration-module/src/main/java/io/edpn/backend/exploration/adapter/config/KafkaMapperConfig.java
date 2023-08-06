package io.edpn.backend.exploration.adapter.config;

import io.edpn.backend.exploration.adapter.kafka.dto.mapper.KafkaMessageMapper;
import io.edpn.backend.exploration.adapter.kafka.dto.mapper.KafkaSystemCoordinatesResponseMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("ExplorationKafkaMapperConfig")
public class KafkaMapperConfig {


    @Bean(name = "explorationSystemCoordinatesResponseMapper")
    public KafkaSystemCoordinatesResponseMapper systemCoordinatesResponseMapper() {
        return new KafkaSystemCoordinatesResponseMapper();
    }

    @Bean(name = "explorationKafkaMessageMapper")
    public KafkaMessageMapper kafkaMessageMapper() {
        return new KafkaMessageMapper();
    }
}
