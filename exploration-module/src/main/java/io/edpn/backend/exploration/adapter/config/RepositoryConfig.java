package io.edpn.backend.exploration.adapter.config;

import io.edpn.backend.exploration.adapter.persistence.MybatisSystemCoordinateRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.MybatisSystemRepository;
import io.edpn.backend.exploration.adapter.persistence.SystemCoordinateRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.SystemRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisSystemCoordinateRequestEntityMapper;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisSystemEntityMapper;
import io.edpn.backend.util.IdGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("ExplorationRepositoryConfig")
public class RepositoryConfig {

    @Bean(name = "explorationSystemRepository")
    public SystemRepository systemRepository(
            MybatisSystemRepository mybatisSystemRepository,
            MybatisSystemEntityMapper mybatisSystemEntityMapper,
            @Qualifier("explorationIdGenerator") IdGenerator idGenerator
    ) {
        return new SystemRepository(mybatisSystemRepository, mybatisSystemEntityMapper, idGenerator);
    }

    @Bean(name = "explorationSystemCoordinateRequestRepository")
    public SystemCoordinateRequestRepository systemCoordinateRequestRepository(
            MybatisSystemCoordinateRequestRepository mybatisSystemCoordinateRequestRepository,
            MybatisSystemCoordinateRequestEntityMapper mybatisSystemCoordinateRequestEntityMapper
    ) {
        return new SystemCoordinateRequestRepository(mybatisSystemCoordinateRequestRepository, mybatisSystemCoordinateRequestEntityMapper);
    }
}
