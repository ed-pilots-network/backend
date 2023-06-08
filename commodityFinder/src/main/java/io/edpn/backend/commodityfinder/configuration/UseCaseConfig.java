package io.edpn.backend.commodityfinder.configuration;

import io.edpn.backend.commodityfinder.application.usecase.DefaultFindCommodityMarketInfoUseCase;
import io.edpn.backend.commodityfinder.application.usecase.DefaultReceiveCommodityMessageUseCase;
import io.edpn.backend.commodityfinder.domain.repository.CommodityRepository;
import io.edpn.backend.commodityfinder.domain.repository.CommodityMarketInfoRepository;
import io.edpn.backend.commodityfinder.domain.repository.StationRepository;
import io.edpn.backend.commodityfinder.domain.repository.SystemRepository;
import io.edpn.backend.commodityfinder.domain.usecase.FindCommodityMarketInfoUseCase;
import io.edpn.backend.commodityfinder.domain.usecase.ReceiveCommodityMessageUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("CommodityFinderUseCaseConfig")
public class UseCaseConfig {

    @Bean
    public FindCommodityMarketInfoUseCase findBestCommodityPriceUseCase(CommodityMarketInfoRepository commodityMarketInfoRepository) {
        return new DefaultFindCommodityMarketInfoUseCase(commodityMarketInfoRepository);
    }

    @Bean
    public ReceiveCommodityMessageUseCase receiveCommodityMessageUseCase(CommodityRepository commodityRepository, SystemRepository systemRepository, StationRepository stationRepository) {
        return new DefaultReceiveCommodityMessageUseCase(commodityRepository, systemRepository, stationRepository);
    }
}
