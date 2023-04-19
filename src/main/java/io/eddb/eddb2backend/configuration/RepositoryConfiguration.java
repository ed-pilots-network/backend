package io.eddb.eddb2backend.configuration;

import io.eddb.eddb2backend.domain.repository.*;
import io.eddb.eddb2backend.infrastructure.persistence.mappers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {

    @Bean
    public CommodityRepository commodityRepository(CommodityEntityMapper commodityEntityMapper) {
        return new io.eddb.eddb2backend.infrastructure.persistence.repository.CommodityRepository(commodityEntityMapper);
    }

    @Bean
    public EconomyRepository economyRepository(EconomyEntityMapper economyEntityMapper) {
        return new io.eddb.eddb2backend.infrastructure.persistence.repository.EconomyRepository(economyEntityMapper);
    }

    @Bean
    public HistoricStationCommodityRepository historicStationCommodityRepository(HistoricStationCommodityEntityMapper historicStationCommodityEntityMapper) {
        return new io.eddb.eddb2backend.infrastructure.persistence.repository.HistoricStationCommodityRepository(historicStationCommodityEntityMapper);
    }

    @Bean
    public StationRepository stationRepository(StationEntityMapper stationEntityMapper) {
        return new io.eddb.eddb2backend.infrastructure.persistence.repository.StationRepository(stationEntityMapper);
    }

    @Bean
    public SystemRepository systemRepository(SystemEntityMapper systemEntityMapper) {
        return new io.eddb.eddb2backend.infrastructure.persistence.repository.SystemRepository(systemEntityMapper);
    }
}
