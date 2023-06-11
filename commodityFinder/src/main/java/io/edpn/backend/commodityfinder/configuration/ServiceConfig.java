package io.edpn.backend.commodityfinder.configuration;

import io.edpn.backend.commodityfinder.application.service.BestCommodityPriceService;
import io.edpn.backend.commodityfinder.application.mappers.CommodityMarketInfoResponseMapper;
import io.edpn.backend.commodityfinder.domain.usecase.FindCommodityMarketInfoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("CommodityFinderServiceConfig")
public class ServiceConfig {

    @Bean
    public BestCommodityPriceService bestCommodityPriceController(FindCommodityMarketInfoUseCase findCommodityMarketInfoUseCase, CommodityMarketInfoResponseMapper commodityMarketInfoResponseMapper){
        return new BestCommodityPriceService(findCommodityMarketInfoUseCase, commodityMarketInfoResponseMapper);
    }
}
