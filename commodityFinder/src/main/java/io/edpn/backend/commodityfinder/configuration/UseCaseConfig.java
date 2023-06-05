package io.edpn.backend.commodityfinder.configuration;

import io.edpn.backend.commodityfinder.application.service.CommodityService;
import io.edpn.backend.commodityfinder.application.service.MarketDatumService;
import io.edpn.backend.commodityfinder.application.service.StationService;
import io.edpn.backend.commodityfinder.application.service.SystemService;
import io.edpn.backend.commodityfinder.application.usecase.DefaultFindBestCommodityPriceUseCase;
import io.edpn.backend.commodityfinder.application.usecase.DefaultReceiveCommodityMessageUseCase;
import io.edpn.backend.commodityfinder.domain.usecase.FindBestCommodityPriceUseCase;
import io.edpn.backend.commodityfinder.domain.usecase.ReceiveCommodityMessageUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public FindBestCommodityPriceUseCase findBestCommodityPriceUseCase(MarketDatumService marketDatumService) {
        return new DefaultFindBestCommodityPriceUseCase(marketDatumService);
    }

    @Bean
    public ReceiveCommodityMessageUseCase receiveCommodityMessageUseCase(CommodityService commodityService, SystemService systemService, StationService stationService) {
        return new DefaultReceiveCommodityMessageUseCase(commodityService, systemService, stationService);
    }
}
