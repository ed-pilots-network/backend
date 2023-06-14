package io.edpn.backend.commodityfinder.configuration;

import io.edpn.backend.commodityfinder.application.usecase.DefaultFindCommodityMarketInfoUseCase;
import io.edpn.backend.commodityfinder.application.usecase.DefaultReceiveApproachSettlementMessageUseCase;
import io.edpn.backend.commodityfinder.application.usecase.DefaultReceiveCommodityMessageUseCase;
import io.edpn.backend.commodityfinder.domain.model.Station;
import io.edpn.backend.commodityfinder.domain.model.System;
import io.edpn.backend.commodityfinder.domain.repository.CommodityMarketInfoRepository;
import io.edpn.backend.commodityfinder.domain.repository.CommodityRepository;
import io.edpn.backend.commodityfinder.domain.repository.StationRepository;
import io.edpn.backend.commodityfinder.domain.repository.SystemRepository;
import io.edpn.backend.commodityfinder.domain.service.RequestDataService;
import io.edpn.backend.commodityfinder.domain.usecase.FindCommodityMarketInfoUseCase;
import io.edpn.backend.commodityfinder.domain.usecase.ReceiveApproachSettlementMessageUseCase;
import io.edpn.backend.commodityfinder.domain.usecase.ReceiveCommodityMessageUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration("CommodityFinderUseCaseConfig")
public class UseCaseConfig {

    @Bean
    public FindCommodityMarketInfoUseCase findBestCommodityPriceUseCase(CommodityMarketInfoRepository commodityMarketInfoRepository) {
        return new DefaultFindCommodityMarketInfoUseCase(commodityMarketInfoRepository);
    }

    @Bean
    public ReceiveCommodityMessageUseCase receiveCommodityMessageUseCase(CommodityRepository commodityRepository, SystemRepository systemRepository, StationRepository stationRepository, List<RequestDataService<Station>> stationRequestDataServices, List<RequestDataService<System>> systemRequestDataServices) {
        return new DefaultReceiveCommodityMessageUseCase(commodityRepository, systemRepository, stationRepository, stationRequestDataServices, systemRequestDataServices);
    }

    @Bean
    public ReceiveApproachSettlementMessageUseCase receiveApproachSettlementMessageUseCase(SystemRepository systemRepository, StationRepository stationRepository) {
        return new DefaultReceiveApproachSettlementMessageUseCase(systemRepository, stationRepository);
    }
}
