package io.edpn.backend.commodityfinder.configuration;

import io.edpn.backend.commodityfinder.application.controller.BestCommodityPriceController;
import io.edpn.backend.commodityfinder.application.mappers.CommodityMarketInfoResponseMapper;
import io.edpn.backend.commodityfinder.domain.usecase.FindCommodityMarketInfoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("CommodityFinderControllerConfig")
public class ControllerConfig {

    @Bean
    public BestCommodityPriceController bestCommodityPriceController(FindCommodityMarketInfoUseCase findCommodityMarketInfoUseCase, CommodityMarketInfoResponseMapper commodityMarketInfoResponseMapper){
        return new BestCommodityPriceController(findCommodityMarketInfoUseCase, commodityMarketInfoResponseMapper);
    }
}
