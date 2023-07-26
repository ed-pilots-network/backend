package io.edpn.backend.exploration.configuration;

import io.edpn.backend.exploration.domain.repository.RequestDataMessageRepository;
import io.edpn.backend.exploration.domain.repository.SystemCoordinateDataRequestRepository;
import io.edpn.backend.exploration.domain.repository.SystemRepository;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.entity.RequestDataMessageMapper;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.entity.SystemCoordinateDataRequestMapper;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.entity.SystemMapper;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.mybatis.RequestDataMessageEntityMapper;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.mybatis.SystemCoordinateDataRequestEntityMapper;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.mybatis.SystemEntityMapper;
import io.edpn.backend.exploration.infrastructure.persistence.repository.MybatisRequestDataMessageRepository;
import io.edpn.backend.exploration.infrastructure.persistence.repository.MybatisSystemCoordinateDataRequestRepository;
import io.edpn.backend.exploration.infrastructure.persistence.repository.MybatisSystemRepository;
import io.edpn.backend.util.IdGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("ExplorationModuleRepositoryConfig")
public class RepositoryConfig {

    @Bean(name = "explorationSystemRepository")
    public SystemRepository systemRepository(@Qualifier("explorationIdGenerator") IdGenerator idGenerator, SystemMapper systemMapper, SystemEntityMapper systemEntityMapper) {
        return new MybatisSystemRepository(idGenerator, systemMapper, systemEntityMapper);
    }

    @Bean(name = "explorationSystemCoordinateDataRequestRepository")
    public SystemCoordinateDataRequestRepository systemCoordinateDataRequestRepository(SystemCoordinateDataRequestMapper systemCoordinateDataRequestMapper, SystemCoordinateDataRequestEntityMapper systemCoordinateDataRequestEntityMapper) {
        return new MybatisSystemCoordinateDataRequestRepository(systemCoordinateDataRequestMapper, systemCoordinateDataRequestEntityMapper);
    }

    @Bean(name = "explorationRequestDataMessageRepository")
    public RequestDataMessageRepository requestDataMessageRepository(RequestDataMessageEntityMapper requestDataMessageEntityMapper, RequestDataMessageMapper requestDataMessageMapper) {
        return new MybatisRequestDataMessageRepository(requestDataMessageEntityMapper, requestDataMessageMapper);
    }
}
