package io.edpn.backend.exploration.configuration;

import io.edpn.backend.exploration.infrastructure.persistence.mappers.entity.RequestDataMessageMapper;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.entity.SystemCoordinateDataRequestMapper;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.entity.SystemMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("ExplorationModuleEntityMapperConfig")
public class EntityMapperConfig {

    @Bean(name = "explorationSystemMapper")
    public SystemMapper systemMapper() {
        return new SystemMapper();
    }

    @Bean(name = "explorationSystemCoordinateDataRequestMapper")
    public SystemCoordinateDataRequestMapper systemCoordinateDataRequestMapper() {
        return new SystemCoordinateDataRequestMapper();
    }

    @Bean(name = "explorationRequestDataMessageMapper")
    public RequestDataMessageMapper requestDataMessageMapper() {
        return new RequestDataMessageMapper();
    }
}
