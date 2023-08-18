package io.edpn.backend.trade.adapter.config;

import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistenceLocateCommodityFilterMapper;
import io.edpn.backend.trade.application.dto.web.filter.mapper.FindCommodityFilterDtoMapper;
import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistenceFindCommodityFilterMapper;
import io.edpn.backend.trade.application.dto.web.filter.mapper.LocateCommodityFilterDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.CommodityMarketInfoDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.LocateCommodityDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.ValidatedCommodityDtoMapper;
import io.edpn.backend.trade.application.port.outgoing.commoditymarketinfo.GetFullCommodityMarketInfoPort;
import io.edpn.backend.trade.application.port.outgoing.locatecommodity.LocateCommodityByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadAllValidatedCommodityPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByNamePort;
import io.edpn.backend.trade.application.service.FindCommodityMarketInfoService;
import io.edpn.backend.trade.application.service.FindCommodityService;
import io.edpn.backend.trade.application.service.LocateCommodityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("TradeServiceConfig")
public class ServiceConfig {

    @Bean(name = "tradeFindCommodityService")
    public FindCommodityService findCommodityService(
            LoadAllValidatedCommodityPort loadAllValidatedCommodityPort,
            LoadValidatedCommodityByNamePort loadValidatedCommodityByNamePort,
            LoadValidatedCommodityByFilterPort loadValidatedCommodityByFilterPort,
            ValidatedCommodityDtoMapper validatedCommodityDTOMapper,
            FindCommodityFilterDtoMapper findCommodityFilterDtoMapper,
            PersistenceFindCommodityFilterMapper persistenceFindCommodityFilterMapper) {
        return new FindCommodityService(loadAllValidatedCommodityPort, loadValidatedCommodityByNamePort, loadValidatedCommodityByFilterPort, validatedCommodityDTOMapper, findCommodityFilterDtoMapper, persistenceFindCommodityFilterMapper);
    }
    
    @Bean(name = "tradeFindCommodityMarketInfoService")
    public FindCommodityMarketInfoService findCommodityMarketInfoService(
            GetFullCommodityMarketInfoPort commodityMarketInfoPort,
            CommodityMarketInfoDtoMapper commodityMarketInfoDtoMapper) {
       return new FindCommodityMarketInfoService(commodityMarketInfoPort, commodityMarketInfoDtoMapper);
    }
    
    @Bean(name = "tradeLocateCommodityService")
    public LocateCommodityService locateCommodityService(
            LocateCommodityByFilterPort locateCommodityByFilterPort,
            PersistenceLocateCommodityFilterMapper locateCommodityFilterMapper,
            LocateCommodityFilterDtoMapper locateCommodityFilterDtoMapper,
            LocateCommodityDtoMapper locateCommodityDtoMapper) {
        return new LocateCommodityService(locateCommodityByFilterPort, locateCommodityFilterMapper, locateCommodityFilterDtoMapper, locateCommodityDtoMapper);
    }
}
