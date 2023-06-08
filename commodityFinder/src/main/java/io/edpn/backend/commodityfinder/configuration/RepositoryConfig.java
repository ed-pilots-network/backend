package io.edpn.backend.commodityfinder.configuration;

import io.edpn.backend.commodityfinder.domain.repository.CommodityRepository;
import io.edpn.backend.commodityfinder.domain.repository.CommodityMarketInfoRepository;
import io.edpn.backend.commodityfinder.domain.repository.StationRepository;
import io.edpn.backend.commodityfinder.domain.repository.SystemRepository;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.entity.CommodityMarketInfoMapper;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.entity.CommodityMapper;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.entity.StationMapper;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.entity.SystemMapper;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis.CommodityMarketInfoEntityMapper;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis.CommodityEntityMapper;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis.MarketDatumEntityMapper;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis.StationEntityMapper;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis.SystemEntityMapper;
import io.edpn.backend.commodityfinder.infrastructure.persistence.repository.MybatisCommodityRepository;
import io.edpn.backend.commodityfinder.infrastructure.persistence.repository.MybatisCommodityMarketInfoRepository;
import io.edpn.backend.commodityfinder.infrastructure.persistence.repository.MybatisStationRepository;
import io.edpn.backend.commodityfinder.infrastructure.persistence.repository.MybatisSystemRepository;
import io.edpn.backend.util.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("CommodityFinderRepositoryConfig")
public class RepositoryConfig {

    @Bean
    public CommodityRepository commodityRepository(IdGenerator idGenerator, CommodityMapper commodityMapper, CommodityEntityMapper commodityEntityMapper) {
        return new MybatisCommodityRepository(idGenerator, commodityMapper, commodityEntityMapper);
    }

    @Bean
    public CommodityMarketInfoRepository marketDatumRepository(CommodityMarketInfoMapper commodityMarketInfoMapper, CommodityEntityMapper commodityEntityMapper, CommodityMarketInfoEntityMapper commodityMarketInfoEntityMapper) {
        return new MybatisCommodityMarketInfoRepository(commodityMarketInfoMapper, commodityEntityMapper, commodityMarketInfoEntityMapper);
    }

    @Bean
    public StationRepository stationRepository(IdGenerator idGenerator, StationMapper stationMapper, StationEntityMapper stationEntityMapper, MarketDatumEntityMapper marketDatumEntityMapper) {
        return new MybatisStationRepository(idGenerator, stationMapper, stationEntityMapper, marketDatumEntityMapper);
    }

    @Bean
    public SystemRepository systemRepository(IdGenerator idGenerator, SystemMapper systemMapper, SystemEntityMapper systemEntityMapper) {
        return new MybatisSystemRepository(idGenerator, systemMapper, systemEntityMapper);
    }
}
