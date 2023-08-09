package io.edpn.backend.trade.adapter.config;

import io.edpn.backend.trade.adapter.web.dto.mapper.RestFindCommodityDTOMapper;
import io.edpn.backend.trade.adapter.web.dto.mapper.RestValidatedCommodityDTOMapper;
import io.edpn.backend.trade.application.dto.mapper.FindCommodityDTOMapper;
import io.edpn.backend.trade.application.dto.mapper.ValidatedCommodityDTOMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("TradeWebDtoMapperConfig")
public class WebDtoMapperConfig {
    
    @Bean(name = "tradeFindCommodityDTOMapper")
    public FindCommodityDTOMapper findCommodityDTOMapper() {
        return new RestFindCommodityDTOMapper();
    }
    
    @Bean(name = "tradeValidatedCommodityDTOMapper")
    public ValidatedCommodityDTOMapper validatedCommodityDTOMapper() {
        return new RestValidatedCommodityDTOMapper();
    }
}
