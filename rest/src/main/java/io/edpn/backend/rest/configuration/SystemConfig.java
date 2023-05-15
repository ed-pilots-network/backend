package io.edpn.backend.rest.configuration;

import io.edpn.backend.rest.application.service.system.GetSystemService;
import io.edpn.backend.rest.application.usecase.system.GetSystemUseCase;
import io.edpn.backend.rest.infrastructure.persistence.mappers.system.SystemMapper;
import io.edpn.backend.rest.infrastructure.persistence.repository.system.SystemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemConfig {
    
    @Bean
    public io.edpn.backend.rest.domain.repository.system.SystemRepository systemRepository(SystemMapper systemMapper) {
        return new SystemRepository(systemMapper);
    }
    
    @Bean
    public GetSystemUseCase getSystemUseCase(io.edpn.backend.rest.domain.repository.system.SystemRepository systemRepository){
        return new GetSystemService(systemRepository);
    }
}
