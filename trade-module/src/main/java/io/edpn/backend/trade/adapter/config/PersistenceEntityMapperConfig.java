package io.edpn.backend.trade.adapter.config;

import io.edpn.backend.trade.adapter.persistence.entity.mapper.CommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.CommodityMarketInfoEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.LocateCommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MarketDatumEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.StationDataRequestEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.SystemDataRequestEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.SystemEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.ValidatedCommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.FindCommodityFilterMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.FindStationFilterMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.FindSystemFilterMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.LocateCommodityFilterMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("TradeModuleEntityMapperConfig")
public class PersistenceEntityMapperConfig {

    @Bean(name = "tradeValidatedCommodityEntityMapper")
    public ValidatedCommodityEntityMapper validatedCommodityEntityMapper() {
        return new ValidatedCommodityEntityMapper();
    }

    @Bean(name = "tradeFindCommodityEntityMapper")
    public FindCommodityFilterMapper findCommodityFilterMapper() {
        return new FindCommodityFilterMapper();
    }

    @Bean(name = "tradeLocateCommodityEntityMapper")
    public LocateCommodityFilterMapper locateCommodityFilterMapper() {
        return new LocateCommodityFilterMapper();
    }

    @Bean(name = "tradePersistenceFindSystemFilterMapper")
    public FindSystemFilterMapper persistenceFindSystemFilterMapper() {
        return new FindSystemFilterMapper();
    }

    @Bean(name = "tradeSystemEntityMapper")
    public SystemEntityMapper systemEntityMapper() {
        return new SystemEntityMapper();
    }

    @Bean(name = "tradeCommodityEntityMapper")
    public CommodityEntityMapper commodityEntityMapper() {
        return new CommodityEntityMapper();
    }

    @Bean(name = "tradeMarketDatumEntityMapper")
    public MarketDatumEntityMapper marketDatumEntityMapper(
            CommodityEntityMapper commodityEntityMapper) {
        return new MarketDatumEntityMapper(commodityEntityMapper);
    }

    @Bean(name = "tradeStationEntityMapper")
    public StationEntityMapper stationEntityMapper(
            SystemEntityMapper systemEntityMapper,
            MarketDatumEntityMapper marketDatumEntityMapper) {
        return new StationEntityMapper(systemEntityMapper, marketDatumEntityMapper);
    }

    @Bean(name = "tradeLocateCommodityFilterMapper")
    public LocateCommodityEntityMapper locateCommodityEntityMapper(
            ValidatedCommodityEntityMapper validatedCommodityEntityMapper,
            StationEntityMapper stationEntityMapper) {
        return new LocateCommodityEntityMapper(validatedCommodityEntityMapper, stationEntityMapper);
    }

    @Bean(name = "tradeCommodityMarketInfoEntityMapper")
    public CommodityMarketInfoEntityMapper commodityMarketInfoEntityMapper(
            ValidatedCommodityEntityMapper validatedCommodityEntityMapper,
            StationEntityMapper stationEntityMapper) {
        return new CommodityMarketInfoEntityMapper(validatedCommodityEntityMapper, stationEntityMapper);
    }

    @Bean(name = "tradePersistenceFindStationFilterMapper")
    public FindStationFilterMapper persistenceFindStationFilterMapper() {
        return new FindStationFilterMapper();
    }

    @Bean(name = "tradeStationDataRequestEntityMapper")
    public StationDataRequestEntityMapper stationDataRequestEntityMapper() {
        return new StationDataRequestEntityMapper();
    }

    @Bean(name = "tradeSystemDataRequestEntityMapper")
    public SystemDataRequestEntityMapper systemDataRequestEntityMapper() {
        return new SystemDataRequestEntityMapper();
    }
}
