package io.edpn.backend.exploration.adapter.config;

import io.edpn.backend.exploration.adapter.kafka.dto.mapper.KafkaMessageMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("ExplorationKafkaMapperConfig")
public class KafkaMapperConfig {


    @Bean(name = "explorationKafkaMessageMapper")
    public KafkaMessageMapper kafkaMessageMapper() {
        return new KafkaMessageMapper();
    }
}
