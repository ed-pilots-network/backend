package io.edpn.backend.exploration.adapter.config;

import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisSystemCoordinateRequestEntityMapper;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisSystemEliteIdRequestEntityMapper;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisSystemEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("ExplorationModuleEntityMapperConfig")
public class PersistenceEntityMapperConfig {

    @Bean(name = "explorationSystemEntityMapper")
    public MybatisSystemEntityMapper systemEntityMapper() {
        return new MybatisSystemEntityMapper();
    }

    @Bean(name = "explorationSystemCoordinateRequestEntityMapper")
    public MybatisSystemCoordinateRequestEntityMapper systemCoordinateRequestEntityMapper() {
        return new MybatisSystemCoordinateRequestEntityMapper();
    }

    @Bean(name = "explorationSystemEliteIdRequestEntityMapper")
    public MybatisSystemEliteIdRequestEntityMapper systemEliteIdRequestEntityMapper() {
        return new MybatisSystemEliteIdRequestEntityMapper();
    }
}
