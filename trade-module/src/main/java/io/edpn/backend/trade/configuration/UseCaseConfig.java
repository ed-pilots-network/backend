package io.edpn.backend.trade.configuration;

import io.edpn.backend.trade.application.usecase.DefaultFindCommodityMarketInfoUseCase;
import io.edpn.backend.trade.application.usecase.DefaultReceiveCommodityMessageUseCase;
import io.edpn.backend.trade.application.usecase.ReceiveStationArrivalDistanceResponseUseCase;
import io.edpn.backend.trade.application.usecase.ReceiveStationMaxLandingPadSizeResponseUseCase;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.domain.repository.CommodityMarketInfoRepository;
import io.edpn.backend.trade.domain.repository.CommodityRepository;
import io.edpn.backend.trade.domain.repository.MarketDatumRepository;
import io.edpn.backend.trade.domain.repository.StationRepository;
import io.edpn.backend.trade.domain.repository.SystemRepository;
import io.edpn.backend.trade.domain.service.RequestDataService;
import io.edpn.backend.trade.domain.usecase.FindCommodityMarketInfoUseCase;
import io.edpn.backend.trade.domain.usecase.ReceiveCommodityMessageUseCase;
import io.edpn.backend.trade.domain.usecase.ReceiveDataRequestResponseUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationArrivalDistanceResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationMaxLandingPadSizeResponse;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("TradeModuleUseCaseConfig")
public class UseCaseConfig {

    @Bean(name = "findBestCommodityPriceUseCase")
    public FindCommodityMarketInfoUseCase findBestCommodityPriceUseCase(CommodityMarketInfoRepository commodityMarketInfoRepository) {
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
}
