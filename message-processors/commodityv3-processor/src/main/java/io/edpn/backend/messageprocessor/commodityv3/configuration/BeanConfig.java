package io.edpn.backend.messageprocessor.commodityv3.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessor.domain.util.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public IdGenerator idGenerator() {
        return new IdGenerator();
    }
}
