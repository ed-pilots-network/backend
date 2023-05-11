package io.edpn.backend.rest.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.rest.application.service.GetStationService;
import io.edpn.backend.rest.application.usecase.GetStationUsecase;
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
    public GetStationUsecase getStationUsecase(GetStationService getStationService) {
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
