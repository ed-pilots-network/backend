package io.edpn.backend.commodityfinder.configuration;

import io.edpn.backend.commodityfinder.application.usecase.DefaultFindBestCommodityPriceUseCase;
import io.edpn.backend.commodityfinder.application.usecase.DefaultReceiveCommodityMessageUseCase;
import io.edpn.backend.commodityfinder.domain.repository.CommodityRepository;
import io.edpn.backend.commodityfinder.domain.repository.MarketDatumRepository;
import io.edpn.backend.commodityfinder.domain.repository.StationRepository;
import io.edpn.backend.commodityfinder.domain.repository.SystemRepository;
import io.edpn.backend.commodityfinder.domain.usecase.FindBestCommodityPriceUseCase;
import io.edpn.backend.commodityfinder.domain.usecase.ReceiveCommodityMessageUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public FindBestCommodityPriceUseCase findBestCommodityPriceUseCase(MarketDatumRepository marketDatumRepository) {
        return new DefaultFindBestCommodityPriceUseCase(marketDatumRepository);
    }

    @Bean
    public ReceiveCommodityMessageUseCase receiveCommodityMessageUseCase(CommodityRepository commodityRepository, SystemRepository systemRepository, StationRepository stationRepository) {
        return new DefaultReceiveCommodityMessageUseCase(commodityRepository, systemRepository, stationRepository);
    }
}
