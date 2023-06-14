package io.edpn.backend.commodityfinder.configuration;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationArrivalDistanceResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationMaxLandingPadSizeResponse;
import io.edpn.backend.commodityfinder.application.usecase.DefaultFindCommodityMarketInfoUseCase;
import io.edpn.backend.commodityfinder.application.usecase.DefaultReceiveCommodityMessageUseCase;
import io.edpn.backend.commodityfinder.application.usecase.ReceiveStationArrivalDistanceResponseUseCase;
import io.edpn.backend.commodityfinder.application.usecase.ReceiveStationMaxLandingPadSizeResponseUseCase;
import io.edpn.backend.commodityfinder.domain.model.Station;
import io.edpn.backend.commodityfinder.domain.model.System;
import io.edpn.backend.commodityfinder.domain.repository.CommodityMarketInfoRepository;
import io.edpn.backend.commodityfinder.domain.repository.CommodityRepository;
import io.edpn.backend.commodityfinder.domain.repository.StationRepository;
import io.edpn.backend.commodityfinder.domain.repository.SystemRepository;
import io.edpn.backend.commodityfinder.domain.service.RequestDataService;
import io.edpn.backend.commodityfinder.domain.usecase.FindCommodityMarketInfoUseCase;
import io.edpn.backend.commodityfinder.domain.usecase.ReceiveCommodityMessageUseCase;
import io.edpn.backend.commodityfinder.domain.usecase.ReceiveDataRequestResponseUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration("CommodityFinderUseCaseConfig")
public class UseCaseConfig {

    @Bean(name = "findBestCommodityPriceUseCase")
    public FindCommodityMarketInfoUseCase findBestCommodityPriceUseCase(CommodityMarketInfoRepository commodityMarketInfoRepository) {
        return new DefaultFindCommodityMarketInfoUseCase(commodityMarketInfoRepository);
    }

    @Bean(name = "receiveCommodityMessageUseCase")
    public ReceiveCommodityMessageUseCase receiveCommodityMessageUseCase(CommodityRepository commodityRepository, SystemRepository systemRepository, StationRepository stationRepository, List<RequestDataService<Station>> stationRequestDataServices, List<RequestDataService<System>> systemRequestDataServices) {
        return new DefaultReceiveCommodityMessageUseCase(commodityRepository, systemRepository, stationRepository, stationRequestDataServices, systemRequestDataServices);
    }

    @Bean(name = "receiveStationArrivalDistanceResponseUseCase")
    public ReceiveDataRequestResponseUseCase<StationArrivalDistanceResponse> receiveStationArrivalDistanceResponseUseCase(SystemRepository systemRepository, StationRepository stationRepository) {
        return new ReceiveStationArrivalDistanceResponseUseCase(systemRepository, stationRepository);
    }

    @Bean(name = "receiveStationMaxLandingPadSizeResponseUseCase")
    public ReceiveDataRequestResponseUseCase<StationMaxLandingPadSizeResponse> receiveStationMaxLandingPadSizeResponseUseCase(SystemRepository systemRepository, StationRepository stationRepository) {
        return new ReceiveStationMaxLandingPadSizeResponseUseCase(systemRepository, stationRepository);
    }
}
