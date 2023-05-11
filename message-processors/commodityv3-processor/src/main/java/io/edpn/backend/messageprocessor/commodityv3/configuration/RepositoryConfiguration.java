package io.edpn.backend.messageprocessor.commodityv3.configuration;

import io.edpn.backend.messageprocessor.commodityv3.domain.repository.*;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers.*;
import io.edpn.backend.messageprocessor.domain.util.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {

    @Bean
    public CommodityRepository commodityRepository(IdGenerator idGenerator, CommodityEntityMapper commodityEntityMapper) {
        return new io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository.CommodityRepository(idGenerator, commodityEntityMapper);
    }

    @Bean
    public EconomyRepository economyRepository(EconomyEntityMapper economyEntityMapper) {
        return new io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository.EconomyRepository(economyEntityMapper);
    }

    @Bean
    public HistoricStationCommodityMarketDatumRepository historicStationCommodityRepository(HistoricStationCommodityMarketDatumEntityMapper historicStationCommodityMarketDatumEntityMapper) {
        return new io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository.HistoricStationCommodityMarketDatumRepository(historicStationCommodityMarketDatumEntityMapper);
    }

    @Bean
    public StationRepository stationRepository(StationEntityMapper stationEntityMapper) {
        return new io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository.StationRepository(stationEntityMapper);
    }

    @Bean
    public SystemRepository systemRepository(SystemEntityMapper systemEntityMapper) {
        return new io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository.SystemRepository(systemEntityMapper);
    }
}
