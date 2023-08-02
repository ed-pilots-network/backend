package io.edpn.backend.exploration.adapter.config;

import io.edpn.backend.exploration.adapter.persistence.MybatisSystemCoordinateRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.MybatisSystemRepository;
import io.edpn.backend.exploration.adapter.persistence.SystemCoordinateRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.SystemRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.SystemCoordinateRequestEntityMapper;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.SystemEntityMapper;
import io.edpn.backend.util.IdGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("ExplorationRepositoryConfig")
public class RepositoryConfig {

    @Bean(name = "explorationSystemRepository")
    public SystemRepository systemRepository(
            MybatisSystemRepository mybatisSystemRepository,
            SystemEntityMapper systemEntityMapper,
            @Qualifier("explorationIdGenerator") IdGenerator idGenerator
    ) {
        return new SystemRepository(mybatisSystemRepository, systemEntityMapper, idGenerator);
    }

    @Bean(name = "explorationSystemCoordinateRequestRepository")
    public SystemCoordinateRequestRepository systemCoordinateRequestRepository(
            MybatisSystemCoordinateRequestRepository mybatisSystemCoordinateRequestRepository,
            SystemCoordinateRequestEntityMapper systemCoordinateRequestEntityMapper
    ) {
        return new SystemCoordinateRequestRepository(mybatisSystemCoordinateRequestRepository, systemCoordinateRequestEntityMapper);
    }
}
