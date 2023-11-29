package io.edpn.backend.exploration.adapter.config;

import io.edpn.backend.exploration.adapter.kafka.dto.mapper.KafkaMessageMapper;
import io.edpn.backend.exploration.adapter.kafka.dto.mapper.KafkaStationMaxLandingPadSizeResponseMapper;
import io.edpn.backend.exploration.adapter.kafka.dto.mapper.KafkaSystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.adapter.kafka.dto.mapper.KafkaSystemEliteIdResponseMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("ExplorationKafkaMapperConfig")
public class KafkaMapperConfig {


    @Bean(name = "explorationSystemCoordinatesResponseMapper")
    public KafkaSystemCoordinatesResponseMapper systemCoordinatesResponseMapper() {
        return new KafkaSystemCoordinatesResponseMapper();
    }

    @Bean(name = "explorationSystemEliteIdResponseMapper")
    public KafkaSystemEliteIdResponseMapper systemEliteIdResponseMapper() {
        return new KafkaSystemEliteIdResponseMapper();
    }

    @Bean(name = "explorationStationMaxLandingPadSizeResponseMapper")
    public KafkaStationMaxLandingPadSizeResponseMapper stationMaxLandingPadSizeResponseMapper() {
        return new KafkaStationMaxLandingPadSizeResponseMapper();
    }

    @Bean(name = "explorationKafkaMessageMapper")
    public KafkaMessageMapper kafkaMessageMapper() {
        return new KafkaMessageMapper();
    }
}
