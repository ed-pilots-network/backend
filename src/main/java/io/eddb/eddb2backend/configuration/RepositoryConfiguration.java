package io.eddb.eddb2backend.configuration;

import io.eddb.eddb2backend.domain.repository.CommodityRepository;
import io.eddb.eddb2backend.domain.repository.EconomyRepository;
import io.eddb.eddb2backend.domain.repository.HistoricStationCommodityRepository;
import io.eddb.eddb2backend.domain.repository.SchemaLatestTimestampRepository;
import io.eddb.eddb2backend.domain.repository.StationRepository;
import io.eddb.eddb2backend.domain.repository.SystemRepository;
import io.eddb.eddb2backend.infrastructure.persistence.mappers.CommodityEntityMapper;
import io.eddb.eddb2backend.infrastructure.persistence.mappers.EconomyEntityMapper;
import io.eddb.eddb2backend.infrastructure.persistence.mappers.HistoricStationCommodityEntityMapper;
import io.eddb.eddb2backend.infrastructure.persistence.mappers.SchemaLatestTimestampEntityMapper;
import io.eddb.eddb2backend.infrastructure.persistence.mappers.StationEntityMapper;
import io.eddb.eddb2backend.infrastructure.persistence.mappers.SystemEntityMapper;
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

    @Bean
    public SchemaLatestTimestampRepository schemaLatestTimestampRepository(SchemaLatestTimestampEntityMapper schemaLatestTimestampEntityMapper) {
        return new io.eddb.eddb2backend.infrastructure.persistence.repository.SchemaLatestTimestampRepository(schemaLatestTimestampEntityMapper);
    }
}
