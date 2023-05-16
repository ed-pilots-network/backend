package io.edpn.backend.messageprocessor.fsssignaldiscoveredv1.configuration;

import io.edpn.backend.messageprocessor.fsssignaldiscoveredv1.domain.repository.SystemRepository;
import io.edpn.backend.messageprocessor.fsssignaldiscoveredv1.infrastructure.persistence.mapper.SystemEntityMapper;
import io.edpn.backend.messageprocessor.fsssignaldiscoveredv1.infrastructure.persistence.repository.MybatisSystemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {


    @Bean
    public SystemRepository systemRepository(SystemEntityMapper systemEntityMapper) {
        return new MybatisSystemRepository(systemEntityMapper);
    }

}
