package io.edpn.backend.commodityfinder.configuration;

import io.edpn.backend.commodityfinder.domain.repository.CommodityRepository;
import io.edpn.backend.commodityfinder.domain.repository.MarketDatumRepository;
import io.edpn.backend.commodityfinder.domain.repository.StationRepository;
import io.edpn.backend.commodityfinder.domain.repository.SystemRepository;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.CommodityEntityMapper;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.MarketDatumEntityMapper;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.StationEntityMapper;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.SystemEntityMapper;
import io.edpn.backend.commodityfinder.infrastructure.persistence.repository.MybatisCommodityRepository;
import io.edpn.backend.commodityfinder.infrastructure.persistence.repository.MybatisMarketDatumRepository;
import io.edpn.backend.commodityfinder.infrastructure.persistence.repository.MybatisStationRepository;
import io.edpn.backend.commodityfinder.infrastructure.persistence.repository.MybatisSystemRepository;
import io.edpn.backend.util.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public CommodityRepository commodityRepository(IdGenerator idGenerator, CommodityEntityMapper commodityEntityMapper) {
        return new MybatisCommodityRepository(idGenerator, commodityEntityMapper);
    }

    @Bean
    public MarketDatumRepository marketDatumRepository(MarketDatumEntityMapper marketDatumEntityMapper, CommodityEntityMapper commodityEntityMapper) {
        return new MybatisMarketDatumRepository(marketDatumEntityMapper, commodityEntityMapper);
    }

    @Bean
    public StationRepository stationRepository(IdGenerator idGenerator, StationEntityMapper stationEntityMapper, MarketDatumEntityMapper marketDatumEntityMapper) {
        return new MybatisStationRepository(idGenerator, stationEntityMapper, marketDatumEntityMapper);
    }

    @Bean
    public SystemRepository systemRepository(IdGenerator idGenerator, SystemEntityMapper systemEntityMapper) {
        return new MybatisSystemRepository(idGenerator, systemEntityMapper);
    }
}
