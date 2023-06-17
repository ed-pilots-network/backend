package io.edpn.backend.trade.configuration;

import io.edpn.backend.trade.application.mappers.CommodityMarketInfoResponseMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("TradeModuleRestMapperConfig")
public class RestMapperConfig {

    @Bean
    public CommodityMarketInfoResponseMapper bestCommodityPriceResponseMapper() {
        return new CommodityMarketInfoResponseMapper();
    }
}
