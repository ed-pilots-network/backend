package io.edpn.backend.eddnrest.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.eddnrest.application.service.GetStationService;
import io.edpn.backend.eddnrest.application.usecase.GetStationUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public GetStationService getStationService() {
        return new GetStationService();
    }

    @Bean
    public GetStationUsecase getStationUsecase(GetStationService getStationService) {
        return getStationService;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
