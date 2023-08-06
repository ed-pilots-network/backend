package io.edpn.backend.exploration.adapter.config;

import io.edpn.backend.exploration.adapter.web.dto.mapper.RestSystemDtoMapper;
import io.edpn.backend.exploration.adapter.web.FindSystemsByNameContainingInputValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("ExplorationWebConfig")
public class WebConfig {

    @Bean(name = "explorationSystemDtoMapper")
    public RestSystemDtoMapper systemDtoMapper() {
        return new RestSystemDtoMapper();
    }

    @Bean(name = "explorationFindSystemsByNameContainingInputValidator")
    public FindSystemsByNameContainingInputValidator findSystemsByNameContainingInputValidator() {
        return new FindSystemsByNameContainingInputValidator();
    }
}
