package io.edpn.backend.rest.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.rest.application.service.station.FindStationService;
import io.edpn.backend.rest.application.service.system.FindSystemService;
import io.edpn.backend.rest.application.usecase.station.FindStationUseCase;
import io.edpn.backend.rest.application.usecase.system.FindSystemUseCase;
import io.edpn.backend.rest.domain.repository.system.SystemRepository;
import io.edpn.backend.rest.infrastructure.persistence.mappers.system.SystemMapper;
import io.edpn.backend.rest.infrastructure.persistence.repository.system.MybatisSystemRepository;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public SystemRepository systemRepository(SystemMapper systemMapper) {
        return new MybatisSystemRepository(systemMapper);
    }

    @Bean
    public FindSystemUseCase getSystemUseCase(SystemRepository systemRepository) {
        return new FindSystemService(systemRepository);
    }

    @Bean
    public FindStationUseCase getStationService() {
        return new FindStationService();
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
