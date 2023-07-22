package io.edpn.backend.trade.configuration;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationArrivalDistanceResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationMaxLandingPadSizeResponse;
import io.edpn.backend.trade.application.usecase.*;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.domain.repository.*;
import io.edpn.backend.trade.domain.service.RequestDataService;
import io.edpn.backend.trade.domain.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration("TradeModuleUseCaseConfig")
public class UseCaseConfig {

    @Bean(name = "findBestCommodityPriceUseCase")
    public FindCommodityMarketInfoUseCase findCommodityMarketInfoCase(CommodityMarketInfoRepository commodityMarketInfoRepository) {
        return new DefaultFindCommodityMarketInfoUseCase(commodityMarketInfoRepository);
    }

    @Bean(name = "receiveCommodityMessageUseCase")
    public ReceiveCommodityMessageUseCase receiveCommodityMessageUseCase(CommodityRepository commodityRepository, SystemRepository systemRepository, StationRepository stationRepository, MarketDatumRepository marketDatumRepository, List<RequestDataService<Station>> stationRequestDataServices, List<RequestDataService<System>> systemRequestDataServices) {
        return new DefaultReceiveCommodityMessageUseCase(commodityRepository, systemRepository, stationRepository, marketDatumRepository, stationRequestDataServices, systemRequestDataServices);
    }

    @Bean(name = "receiveStationArrivalDistanceResponseUseCase")
    public ReceiveDataRequestResponseUseCase<StationArrivalDistanceResponse> receiveStationArrivalDistanceResponseUseCase(SystemRepository systemRepository, StationRepository stationRepository) {
        return new ReceiveStationArrivalDistanceResponseUseCase(systemRepository, stationRepository);
    }

    @Bean(name = "receiveStationMaxLandingPadSizeResponseUseCase")
    public ReceiveDataRequestResponseUseCase<StationMaxLandingPadSizeResponse> receiveStationMaxLandingPadSizeResponseUseCase(SystemRepository systemRepository, StationRepository stationRepository) {
        return new ReceiveStationMaxLandingPadSizeResponseUseCase(systemRepository, stationRepository);
    }

    @Bean(name = "locateCommodityUseCase")
    public LocateCommodityUseCase locateCommodityUseCase(LocateCommodityRepository locateCommodityRepository) {
        return new DefaultLocateCommodityUseCase(locateCommodityRepository);
    }
    
    @Bean(name = "findCommodityUseCase")
    public FindCommodityUseCase findCommodityUseCase(ValidatedCommodityRepository validatedCommodityRepository) {
        return new DefaultFindCommodityUseCase(validatedCommodityRepository);
    }
}
