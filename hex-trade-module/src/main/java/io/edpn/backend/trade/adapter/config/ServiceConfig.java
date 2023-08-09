package io.edpn.backend.trade.adapter.config;

import io.edpn.backend.trade.application.dto.mapper.FindCommodityEntityMapper;
import io.edpn.backend.trade.application.dto.mapper.ValidatedCommodityDTOMapper;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadAllValidatedCommodityPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByNamePort;
import io.edpn.backend.trade.application.service.FindCommodityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("TradeServiceConfig")
public class ServiceConfig {
    
    @Bean(name = "tradeFindCommodityService")
    public FindCommodityService findCommodityService(
            LoadAllValidatedCommodityPort loadAllValidatedCommodityPort,
            LoadValidatedCommodityByNamePort loadValidatedCommodityByNamePort,
            LoadValidatedCommodityByFilterPort loadValidatedCommodityByFilterPort,
            ValidatedCommodityDTOMapper validatedCommodityDTOMapper,
            FindCommodityEntityMapper findCommodityEntityMapper) {
        return new FindCommodityService(loadAllValidatedCommodityPort, loadValidatedCommodityByNamePort, loadValidatedCommodityByFilterPort, validatedCommodityDTOMapper, findCommodityEntityMapper);
    }
}
