package io.edpn.backend.trade.adapter.config;

import io.edpn.backend.trade.adapter.web.dto.filter.mapper.FindCommodityFilterDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.filter.mapper.LocateCommodityFilterDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.object.mapper.CommodityMarketInfoDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.object.mapper.LocateCommodityDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.object.mapper.StationDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.object.mapper.SystemDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.object.mapper.ValidatedCommodityDtoMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("TradeWebDtoMapperConfig")
public class WebDtoMapperConfig {

    @Bean(name = "tradeFindCommodityDTOMapper")
    public FindCommodityFilterDtoMapper findCommodityDTOMapper() {
        return new FindCommodityFilterDtoMapper();
    }

    @Bean(name = "tradeValidatedCommodityDTOMapper")
    public ValidatedCommodityDtoMapper validatedCommodityDTOMapper() {
        return new ValidatedCommodityDtoMapper();
    }

    @Bean(name = "tradeSystemDtoMapper")
    public SystemDtoMapper systemDtoMapper() {
        return new SystemDtoMapper();
    }

    @Bean(name = "tradeStationDtoMapper")
    public StationDtoMapper stationDtoMapper(
            SystemDtoMapper systemDtoMapper) {
        return new StationDtoMapper(systemDtoMapper);
    }

    @Bean(name = "tradeLocateCommodityDtoMapper")
    public LocateCommodityDtoMapper locateCommodityDtoMapper(
            StationDtoMapper stationDtoMapper,
            ValidatedCommodityDtoMapper validatedCommodityDtoMapper) {
        return new LocateCommodityDtoMapper(stationDtoMapper, validatedCommodityDtoMapper);
    }

    @Bean(name = "tradeCommodityMarketInfoDtoMapper")
    public CommodityMarketInfoDtoMapper commodityMarketInfoDtoMapper(
            ValidatedCommodityDtoMapper validatedCommodityDtoMapper,
            StationDtoMapper stationDtoMapper) {
        return new CommodityMarketInfoDtoMapper(validatedCommodityDtoMapper, stationDtoMapper);
    }

    @Bean(name = "tradeLocateCommodityFilterDtoMapper")
    public LocateCommodityFilterDtoMapper locateCommodityFilterDtoMapper() {
        return new LocateCommodityFilterDtoMapper();
    }
}
