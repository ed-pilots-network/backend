package io.edpn.backend.messageprocessor.commodityv3.configuration;

import io.edpn.backend.messageprocessor.commodityv3.domain.repository.*;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers.*;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository.MybatisCommodityRepository;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository.MybatisEconomyRepository;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository.MybatisHistoricStationCommodityMarketDatumRepository;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository.MybatisStationEconomyProportionRepository;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository.MybatisStationProhibitedCommodityRepository;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository.MybatisStationRepository;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository.MybatisSystemRepository;
import io.edpn.backend.messageprocessor.domain.util.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {

    @Bean
    public CommodityRepository commodityRepository(IdGenerator idGenerator, CommodityEntityMapper commodityEntityMapper) {
        return new MybatisCommodityRepository(idGenerator, commodityEntityMapper);
    }

    @Bean
    public EconomyRepository economyRepository(IdGenerator idGenerator, EconomyEntityMapper economyEntityMapper) {
        return new MybatisEconomyRepository(idGenerator, economyEntityMapper);
    }

    @Bean
    public HistoricStationCommodityMarketDatumRepository historicStationCommodityRepository(IdGenerator idGenerator, HistoricStationCommodityMarketDatumEntityMapper historicStationCommodityMarketDatumEntityMapper) {
        return new MybatisHistoricStationCommodityMarketDatumRepository(idGenerator, historicStationCommodityMarketDatumEntityMapper);
    }

    @Bean
    public StationEconomyProportionRepository stationEconomyProportionRepository(StationEconomyProportionEntityMapper stationEconomyProportionEntityMapper) {
        return new MybatisStationEconomyProportionRepository(stationEconomyProportionEntityMapper);
    }

    @Bean
    public StationRepository stationRepository(IdGenerator idGenerator, StationEntityMapper stationEntityMapper) {
        return new MybatisStationRepository(idGenerator, stationEntityMapper);
    }

    @Bean
    public SystemRepository systemRepository(IdGenerator idGenerator, SystemEntityMapper systemEntityMapper) {
        return new MybatisSystemRepository(idGenerator, systemEntityMapper);
    }

    @Bean
    public StationProhibitedCommodityRepository stationProhibitedCommodityRepository(StationProhibitedCommodityEntityMapper stationProhibitedCommodityEntityMapper) {
        return new MybatisStationProhibitedCommodityRepository(stationProhibitedCommodityEntityMapper);
    }
}
