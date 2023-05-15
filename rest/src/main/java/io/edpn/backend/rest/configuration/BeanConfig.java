package io.edpn.backend.rest.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.rest.application.service.station.GetStationService;
import io.edpn.backend.rest.application.usecase.station.GetStationUseCase;
import io.edpn.backend.rest.application.usecase.system.GetSystemUseCase;
import io.edpn.backend.rest.infrastructure.persistence.mappers.system.SystemMapper;
import io.edpn.backend.rest.infrastructure.persistence.repository.system.SystemRepository;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public GetStationService getStationService() {
        return new GetStationService();
    }

    @Bean
    public GetStationUseCase getStationUsecase(GetStationService getStationService) {
        return getStationService;
    }
    
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
    
    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.setLazyLoadingEnabled(true);
                configuration.setAggressiveLazyLoading(false);
            }
        };
    }
}
