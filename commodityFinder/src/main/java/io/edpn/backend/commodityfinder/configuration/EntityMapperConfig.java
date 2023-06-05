package io.edpn.backend.commodityfinder.configuration;

import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.entity.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntityMapperConfig {

    @Bean
    public SystemMapper systemMapper() {
        return new SystemMapper();
    }

    @Bean
    public CommodityMapper commodityMapper() {
        return new CommodityMapper();
    }

    @Bean
    public MarketDatumMapper marketDatumMapper(CommodityMapper commodityMapper) {
        return new MarketDatumMapper(commodityMapper);
    }

    @Bean
    public StationMapper stationMapper(SystemMapper systemMapper, MarketDatumMapper marketDatumMapper) {
        return new StationMapper(systemMapper, marketDatumMapper);
    }

    @Bean
    public BestCommodityPriceMapper bestCommodityPriceMapper(CommodityMapper commodityMapper, StationMapper stationMapper) {
        return new BestCommodityPriceMapper(commodityMapper, stationMapper);
    }
}