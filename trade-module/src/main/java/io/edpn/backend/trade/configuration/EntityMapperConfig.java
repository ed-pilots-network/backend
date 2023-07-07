package io.edpn.backend.trade.configuration;


import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("TradeModuleEntityMapperConfig")
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
    public FindCommodityMapper findCommodityMapper(CommodityMapper commodityMapper, SystemMapper systemMapper, StationMapper stationMapper) {
        return new FindCommodityMapper(commodityMapper, systemMapper, stationMapper);
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
    public CommodityMarketInfoMapper bestCommodityPriceMapper(CommodityMapper commodityMapper, StationMapper stationMapper) {
        return new CommodityMarketInfoMapper(commodityMapper, stationMapper);
    }

    @Bean
    public RequestDataMessageMapper requestDataMessageMapper() {
        return new RequestDataMessageMapper();
    }
}
