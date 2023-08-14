package io.edpn.backend.trade.adapter.config;

import io.edpn.backend.trade.adapter.web.dto.filter.mapper.RestFindCommodityFilterDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.object.mapper.RestValidatedCommodityDtoMapper;
import io.edpn.backend.trade.application.dto.web.filter.mapper.FindCommodityFilterDtoMapper;
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
}
