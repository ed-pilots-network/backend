package io.edpn.backend.trade.configuration;

import io.edpn.backend.trade.application.mappers.v1.CommodityMarketInfoResponseMapper;
import io.edpn.backend.trade.application.mappers.v1.FindCommodityDTOMapper;
import io.edpn.backend.trade.application.mappers.v1.LocateCommodityDTOMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("TradeModuleRestMapperConfig")
public class RestMapperConfig {

    @Bean(name = "tradeCommodityMarketInfoResponseMapper")
    public CommodityMarketInfoResponseMapper bestCommodityPriceResponseMapper() {
        return new CommodityMarketInfoResponseMapper();
    }

    @Bean(name = "tradeLocateCommodityDTOMapper")
    public LocateCommodityDTOMapper locateCommodityDTOMapper() {
        return new LocateCommodityDTOMapper();
    }

    @Bean(name = "tradeFindCommodityDTOMapper")
    public FindCommodityDTOMapper findCommodityDTOMapper() {
        return new FindCommodityDTOMapper();
    }
}
