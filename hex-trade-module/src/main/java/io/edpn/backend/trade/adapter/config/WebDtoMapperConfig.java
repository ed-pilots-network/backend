package io.edpn.backend.trade.adapter.config;

import io.edpn.backend.trade.adapter.web.dto.mapper.RestFindCommodityFilterDtoMapper;
import io.edpn.backend.trade.adapter.web.dto.mapper.RestValidatedCommodityDtoMapper;
import io.edpn.backend.trade.application.dto.mapper.FindCommodityFilterDtoMapper;
import io.edpn.backend.trade.application.dto.mapper.ValidatedCommodityDtoMapper;
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
