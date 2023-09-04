package io.edpn.backend.trade.adapter.config;

import io.edpn.backend.trade.adapter.web.dto.filter.mapper.RestFindCommodityFilterDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.filter.mapper.RestLocateCommodityFilterDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.filter.mapper.RestPageFilterDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.object.mapper.RestCommodityMarketInfoDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.object.mapper.RestLocateCommodityDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.object.mapper.RestPageInfoDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.object.mapper.RestStationDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.object.mapper.RestSystemDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.object.mapper.RestValidatedCommodityDtoMapper;
import io.edpn.backend.trade.application.dto.web.filter.mapper.FindCommodityFilterDtoMapper;
import io.edpn.backend.trade.application.dto.web.filter.mapper.LocateCommodityFilterDtoMapper;
import io.edpn.backend.trade.application.dto.web.filter.mapper.PageFilterDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.CommodityMarketInfoDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.LocateCommodityDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.PageInfoDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.StationDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.SystemDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.ValidatedCommodityDtoMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("TradeWebDtoMapperConfig")
public class WebDtoMapperConfig {

    @Bean(name = "tradeFindCommodityDTOMapper")
    public FindCommodityFilterDtoMapper findCommodityDTOMapper() {
        return new RestFindCommodityFilterDtoMapper();
    }

    @Bean(name = "tradeValidatedCommodityDTOMapper")
    public ValidatedCommodityDtoMapper validatedCommodityDTOMapper() {
        return new RestValidatedCommodityDtoMapper();
    }

    @Bean(name = "tradePageInfoDtoMapper")
    public PageInfoDtoMapper pageInfoDtoMapper() {
        return new RestPageInfoDtoMapper();
    }

    @Bean(name = "tradeSystemDtoMapper")
    public SystemDtoMapper systemDtoMapper() {
        return new RestSystemDtoMapper();
    }

    @Bean(name = "tradeStationDtoMapper")
    public StationDtoMapper stationDtoMapper(
            SystemDtoMapper systemDtoMapper) {
        return new RestStationDtoMapper(systemDtoMapper);
    }

    @Bean(name = "tradeLocateCommodityDtoMapper")
    public LocateCommodityDtoMapper locateCommodityDtoMapper(
            StationDtoMapper stationDtoMapper,
            ValidatedCommodityDtoMapper validatedCommodityDtoMapper) {
        return new RestLocateCommodityDtoMapper(stationDtoMapper, validatedCommodityDtoMapper);
    }

    @Bean(name = "tradeCommodityMarketInfoDtoMapper")
    public CommodityMarketInfoDtoMapper commodityMarketInfoDtoMapper(
            ValidatedCommodityDtoMapper validatedCommodityDtoMapper,
            StationDtoMapper stationDtoMapper) {
        return new RestCommodityMarketInfoDtoMapper(validatedCommodityDtoMapper, stationDtoMapper);
    }

    @Bean(name = "tradeLocateCommodityFilterDtoMapper")
    public LocateCommodityFilterDtoMapper locateCommodityFilterDtoMapper(PageFilterDtoMapper pageFilterDtoMapper) {
        return new RestLocateCommodityFilterDtoMapper(pageFilterDtoMapper);
    }

    @Bean(name = "tradePageFilterDtoMapper")
    public PageFilterDtoMapper pageFilterDtoMapper() {
        return new RestPageFilterDtoMapper();
    }
}
