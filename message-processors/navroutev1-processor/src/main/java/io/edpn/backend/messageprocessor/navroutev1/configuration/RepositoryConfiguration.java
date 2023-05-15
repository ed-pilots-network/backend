package io.edpn.backend.messageprocessor.navroutev1.configuration;

import io.edpn.backend.messageprocessor.navroutev1.domain.repository.SystemRepository;
import io.edpn.backend.messageprocessor.navroutev1.infrastructure.persistence.mapper.SystemEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {


    @Bean
    public SystemRepository systemRepository(SystemEntityMapper systemEntityMapper) {
        return new io.edpn.backend.messageprocessor.navroutev1.infrastructure.persistence.repository.SystemRepository(systemEntityMapper);
    }

}