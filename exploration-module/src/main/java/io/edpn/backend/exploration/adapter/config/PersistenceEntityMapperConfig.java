package io.edpn.backend.exploration.adapter.config;

import io.edpn.backend.exploration.adapter.persistence.entity.mapper.SystemEntityMapper;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.SystemCoordinateRequestEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("ExplorationModuleEntityMapperConfig")
public class PersistenceEntityMapperConfig {

    @Bean(name = "explorationSystemEntityMapper")
    public SystemEntityMapper systemEntityMapper() {
        return new SystemEntityMapper();
    }


    @Bean(name = "explorationSystemCoordinateRequestEntityMapper")
    public SystemCoordinateRequestEntityMapper systemCoordinateRequestEntityMapper() {
        return new SystemCoordinateRequestEntityMapper();
    }
}
