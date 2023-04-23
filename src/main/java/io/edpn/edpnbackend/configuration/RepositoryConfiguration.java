package io.edpn.edpnbackend.configuration;

import io.edpn.edpnbackend.domain.repository.*;
import io.edpn.edpnbackend.infrastructure.persistence.mappers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {

    @Bean
    public CommodityRepository commodityRepository(CommodityEntityMapper commodityEntityMapper) {
        return new io.edpn.edpnbackend.infrastructure.persistence.repository.CommodityRepository(commodityEntityMapper);
    }

    @Bean
    public EconomyRepository economyRepository(EconomyEntityMapper economyEntityMapper) {
        return new io.edpn.edpnbackend.infrastructure.persistence.repository.EconomyRepository(economyEntityMapper);
    }

    @Bean
    public HistoricStationCommodityMarketDatumRepository historicStationCommodityRepository(HistoricStationCommodityMarketDatumEntityMapper historicStationCommodityMarketDatumEntityMapper) {
        return new io.edpn.edpnbackend.infrastructure.persistence.repository.HistoricStationCommodityMarketDatumRepository(historicStationCommodityMarketDatumEntityMapper);
    }

    @Bean
    public StationRepository stationRepository(StationEntityMapper stationEntityMapper) {
        return new io.edpn.edpnbackend.infrastructure.persistence.repository.StationRepository(stationEntityMapper);
    }

    @Bean
    public SystemRepository systemRepository(SystemEntityMapper systemEntityMapper) {
        return new io.edpn.edpnbackend.infrastructure.persistence.repository.SystemRepository(systemEntityMapper);
    }

    @Bean
    public SchemaLatestTimestampRepository schemaLatestTimestampRepository(SchemaLatestTimestampEntityMapper schemaLatestTimestampEntityMapper) {
        return new io.edpn.edpnbackend.infrastructure.persistence.repository.SchemaLatestTimestampRepository(schemaLatestTimestampEntityMapper);
    }

    @Bean
    public StationSystemRepository StationSystemRepository(StationSystemEntityMapper stationSystemEntityMapper) {
        return new io.edpn.edpnbackend.infrastructure.persistence.repository.StationSystemRepository(stationSystemEntityMapper);
    }
}
