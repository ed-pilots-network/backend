package io.edpn.backend.commodityfinder.configuration;

import io.edpn.backend.commodityfinder.application.mappers.persistence.BestCommodityPriceMapper;
import io.edpn.backend.commodityfinder.application.mappers.persistence.CommodityMapper;
import io.edpn.backend.commodityfinder.application.mappers.persistence.StationMapper;
import io.edpn.backend.commodityfinder.application.mappers.persistence.SystemMapper;
import io.edpn.backend.commodityfinder.application.service.CommodityService;
import io.edpn.backend.commodityfinder.application.service.MarketDatumService;
import io.edpn.backend.commodityfinder.application.service.StationService;
import io.edpn.backend.commodityfinder.application.service.SystemService;
import io.edpn.backend.commodityfinder.domain.repository.CommodityRepository;
import io.edpn.backend.commodityfinder.domain.repository.MarketDatumRepository;
import io.edpn.backend.commodityfinder.domain.repository.StationRepository;
import io.edpn.backend.commodityfinder.domain.repository.SystemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public CommodityService commodityService(CommodityRepository commodityRepository, CommodityMapper commodityMapper) {
        return new CommodityService(commodityRepository, commodityMapper);
    }

    @Bean
    public MarketDatumService marketDatumService(MarketDatumRepository marketDatumRepository, BestCommodityPriceMapper bestCommodityPriceMapper) {
        return new MarketDatumService(marketDatumRepository, bestCommodityPriceMapper);
    }

    @Bean
    public StationService stationService(StationRepository stationRepository, StationMapper stationMapper, SystemMapper systemMapper) {
        return new StationService(stationRepository, stationMapper, systemMapper);
    }

    @Bean
    public SystemService systemService(SystemRepository systemRepository, SystemMapper systemMapper) {
        return new SystemService(systemRepository, systemMapper);
    }
}
