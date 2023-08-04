package io.edpn.backend.exploration.adapter.config;

import io.edpn.backend.exploration.adapter.web.dto.mapper.RestSystemDtoMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("ExplorationWebDtoMapperConfig")
public class WebDtoMapperConfig {

    @Bean(name = "explorationSystemDtoMapper")
    public RestSystemDtoMapper systemDtoMapper() {
        return new RestSystemDtoMapper();
    }
}
