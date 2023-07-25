package io.edpn.backend.exploration.configuration;

import io.edpn.backend.exploration.application.mappers.v1.SystemDtoMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("ExplorationModuleRestMapperConfig")
public class RestMapperConfig {

    @Bean(name = "explorationSystemDtoMapper")
    public SystemDtoMapper systemDtoMapper() {
        return new SystemDtoMapper();
    }
}
