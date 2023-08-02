package io.edpn.backend.exploration.adapter.config;

import io.edpn.backend.exploration.adapter.web.dto.SystemDtoMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("ExplorationWebDtoMapperConfig")
public class WebDtoMapperConfig {

    @Bean(name = "explorationSystemDtoMapper")
    public SystemDtoMapper systemDtoMapper() {
        return new SystemDtoMapper();
    }
}
