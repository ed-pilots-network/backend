package io.edpn.backend.exploration.adapter.config;

import io.edpn.backend.exploration.adapter.persistence.BodyRepository;
import io.edpn.backend.exploration.adapter.persistence.MybatisBodyRepository;
import io.edpn.backend.exploration.adapter.persistence.MybatisRingRepository;
import io.edpn.backend.exploration.adapter.persistence.MybatisStarRepository;
import io.edpn.backend.exploration.adapter.persistence.MybatisStationArrivalDistanceRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.MybatisStationMaxLandingPadSizeRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.MybatisStationRepository;
import io.edpn.backend.exploration.adapter.persistence.MybatisSystemCoordinateRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.MybatisSystemEliteIdRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.MybatisSystemRepository;
import io.edpn.backend.exploration.adapter.persistence.RingRepository;
import io.edpn.backend.exploration.adapter.persistence.StarRepository;
import io.edpn.backend.exploration.adapter.persistence.StationArrivalDistanceRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.StationMaxLandingPadSizeRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.StationRepository;
import io.edpn.backend.exploration.adapter.persistence.SystemCoordinateRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.SystemEliteIdRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.SystemRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisBodyEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisRingEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStarEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisStationArrivalDistanceRequestEntityMapper;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisStationEntityMapper;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisStationMaxLandingPadSizeRequestEntityMapper;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisSystemCoordinateRequestEntityMapper;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisSystemEliteIdRequestEntityMapper;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisSystemEntityMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.BodyEntityMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.RingEntityMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.StarEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("ExplorationRepositoryConfig")
public class RepositoryConfig {

    @Bean(name = "explorationSystemRepository")
    public SystemRepository systemRepository(
            MybatisSystemRepository mybatisSystemRepository,
            MybatisSystemEntityMapper mybatisSystemEntityMapper
    ) {
        return new SystemRepository(mybatisSystemRepository, mybatisSystemEntityMapper);
    }

    @Bean(name = "explorationStationRepository")
    public StationRepository stationRepository(
            MybatisStationRepository mybatisStationRepository,
            MybatisStationEntityMapper mybatisStationEntityMapper
    ) {
        return new StationRepository(mybatisStationRepository, mybatisStationEntityMapper);
    }

    @Bean(name = "explorationSystemCoordinateRequestRepository")
    public SystemCoordinateRequestRepository systemCoordinateRequestRepository(
            MybatisSystemCoordinateRequestRepository mybatisSystemCoordinateRequestRepository,
            MybatisSystemCoordinateRequestEntityMapper mybatisSystemCoordinateRequestEntityMapper
    ) {
        return new SystemCoordinateRequestRepository(mybatisSystemCoordinateRequestRepository, mybatisSystemCoordinateRequestEntityMapper);
    }

    @Bean(name = "explorationSystemEliteIdRequestRepository")
    public SystemEliteIdRequestRepository systemEliteIdRequestRepository(
            MybatisSystemEliteIdRequestRepository mybatisSystemEliteIdRequestRepository,
            MybatisSystemEliteIdRequestEntityMapper mybatisSystemEliteIdRequestEntityMapper
    ) {
        return new SystemEliteIdRequestRepository(mybatisSystemEliteIdRequestRepository, mybatisSystemEliteIdRequestEntityMapper);
    }
    
    @Bean(name = "explorationStationArrivalDistanceRequestRepository")
    public StationArrivalDistanceRequestRepository stationArrivalDistanceRequestRepository(
            MybatisStationArrivalDistanceRequestRepository mybatisStationArrivalDistanceRequestRepository,
            MybatisStationArrivalDistanceRequestEntityMapper mybatisStationArrivalDistanceRequestEntityMapper
    ) {
        return new StationArrivalDistanceRequestRepository(mybatisStationArrivalDistanceRequestRepository, mybatisStationArrivalDistanceRequestEntityMapper);
    }

    @Bean(name = "explorationStationMaxLandingPadSizeRequestRepository")
    public StationMaxLandingPadSizeRequestRepository stationMaxLandingPadSizeRequestRepository(
            MybatisStationMaxLandingPadSizeRequestRepository mybatisStationMaxLandingPadSizeRequestRepository,
            MybatisStationMaxLandingPadSizeRequestEntityMapper mybatisStationMaxLandingPadSizeRequestEntityMapper
    ) {
        return new StationMaxLandingPadSizeRequestRepository(mybatisStationMaxLandingPadSizeRequestRepository, mybatisStationMaxLandingPadSizeRequestEntityMapper);
    }
    
    @Bean(name = "explorationBodyRepository")
    public BodyRepository bodyRepository(
            MybatisBodyRepository mybatisBodyRepository,
            BodyEntityMapper<MybatisBodyEntity> bodyEntityMapper
    ) {
        return new BodyRepository(mybatisBodyRepository, bodyEntityMapper);
    }
    
    @Bean(name = "explorationStarRepository")
    public StarRepository starRepository(
            MybatisStarRepository mybatisStarRepository,
            StarEntityMapper<MybatisStarEntity> starEntityMapper
    ) {
        return new StarRepository(mybatisStarRepository, starEntityMapper);
    }
    
    @Bean(name = "explorationRingRepository")
    public RingRepository ringRepository(
            MybatisRingRepository mybatisRingRepository,
            RingEntityMapper<MybatisRingEntity> ringEntityMapper
    ) {
        return new RingRepository(mybatisRingRepository, ringEntityMapper);
    }
}
