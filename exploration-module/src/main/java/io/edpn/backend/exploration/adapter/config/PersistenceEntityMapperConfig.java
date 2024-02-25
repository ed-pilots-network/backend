package io.edpn.backend.exploration.adapter.config;

import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisBodyEntityMapper;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisRingEntityMapper;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisStarEntityMapper;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisStationArrivalDistanceRequestEntityMapper;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisStationEntityMapper;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisStationMaxLandingPadSizeRequestEntityMapper;
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

    @Bean(name = "explorationStationEntityMapper")
    public MybatisStationEntityMapper stationEntityMapper(
            MybatisSystemEntityMapper systemEntityMapper
    ) {
        return new MybatisStationEntityMapper(systemEntityMapper);
    }

    @Bean(name = "explorationSystemCoordinateRequestEntityMapper")
    public MybatisSystemCoordinateRequestEntityMapper systemCoordinateRequestEntityMapper() {
        return new MybatisSystemCoordinateRequestEntityMapper();
    }

    @Bean(name = "explorationSystemEliteIdRequestEntityMapper")
    public MybatisSystemEliteIdRequestEntityMapper systemEliteIdRequestEntityMapper() {
        return new MybatisSystemEliteIdRequestEntityMapper();
    }
    
    @Bean(name = "explorationStationArrivalDistanceRequestEntityMapper")
    public MybatisStationArrivalDistanceRequestEntityMapper stationArrivalDistanceRequestEntityMapper() {
        return new MybatisStationArrivalDistanceRequestEntityMapper();
    }

    @Bean(name = "explorationStationMaxLandingPadSizeRequestEntityMapper")
    public MybatisStationMaxLandingPadSizeRequestEntityMapper stationMaxLandingPadSizeRequestEntityMapper() {
        return new MybatisStationMaxLandingPadSizeRequestEntityMapper();
    }
    
    @Bean(name = "explorationRingEntityMapper")
    public MybatisRingEntityMapper ringEntityMapper(){
        return new MybatisRingEntityMapper();
    }
    
    @Bean(name = "explorationBodyEntityMapper")
    public MybatisBodyEntityMapper bodyEntityMapper(MybatisRingEntityMapper ringEntityMapper, MybatisSystemEntityMapper systemEntityMapper) {
        return new MybatisBodyEntityMapper(ringEntityMapper, systemEntityMapper);
    }
    
    @Bean(name = "explorationStarEntityMapper")
    public MybatisStarEntityMapper starEntityMapper(MybatisRingEntityMapper ringEntityMapper, MybatisSystemEntityMapper systemEntityMapper) {
        return new MybatisStarEntityMapper(ringEntityMapper, systemEntityMapper);
    }
}
