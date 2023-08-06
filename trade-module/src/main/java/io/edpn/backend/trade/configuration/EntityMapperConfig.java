package io.edpn.backend.trade.configuration;


import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.CommodityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.CommodityMarketInfoMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.LocateCommodityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.MarketDatumMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.RequestDataMessageMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.StationMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.SystemMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.ValidatedCommodityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("TradeModuleEntityMapperConfig")
public class EntityMapperConfig {

    @Bean(name = "tradeSystemMapper")
    public SystemMapper systemMapper() {
        return new SystemMapper();
    }

    @Bean(name = "tradeCommodityMapper")
    public CommodityMapper commodityMapper() {
        return new CommodityMapper();
    }

    @Bean(name = "tradeLocateCommodityMapper")
    public LocateCommodityMapper locateCommodityMapper(ValidatedCommodityMapper validatedCommodityMapper, SystemMapper systemMapper, StationMapper stationMapper) {
        return new LocateCommodityMapper(validatedCommodityMapper, systemMapper, stationMapper);
    }

    @Bean(name = "tradeValidatedCommodityMapper")
    public ValidatedCommodityMapper validatedCommodityMapper() {
        return new ValidatedCommodityMapper();
    }

    @Bean(name = "tradeMarketDatumMapper")
    public MarketDatumMapper marketDatumMapper(CommodityMapper commodityMapper) {
        return new MarketDatumMapper(commodityMapper);
    }

    @Bean(name = "tradeStationMapper")
    public StationMapper stationMapper(SystemMapper systemMapper, MarketDatumMapper marketDatumMapper) {
        return new StationMapper(systemMapper, marketDatumMapper);
    }

    @Bean(name = "tradeCommodityMarketInfoMapper")
    public CommodityMarketInfoMapper bestCommodityPriceMapper(ValidatedCommodityMapper validatedCommodityMapper, StationMapper stationMapper) {
        return new CommodityMarketInfoMapper(validatedCommodityMapper, stationMapper);
    }

    @Bean
    public RequestDataMessageMapper requestDataMessageMapper() {
        return new RequestDataMessageMapper();
    }
}
