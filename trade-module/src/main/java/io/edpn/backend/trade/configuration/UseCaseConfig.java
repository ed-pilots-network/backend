package io.edpn.backend.trade.configuration;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationArrivalDistanceResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationMaxLandingPadSizeResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemEliteIdResponse;
import io.edpn.backend.trade.application.usecase.DefaultFindCommodityMarketInfoUseCase;
import io.edpn.backend.trade.application.usecase.DefaultFindCommodityUseCase;
import io.edpn.backend.trade.application.usecase.DefaultLocateCommodityUseCase;
import io.edpn.backend.trade.application.usecase.DefaultReceiveCommodityMessageUseCase;
import io.edpn.backend.trade.application.usecase.ReceiveStationArrivalDistanceResponseUseCase;
import io.edpn.backend.trade.application.usecase.ReceiveStationMaxLandingPadSizeResponseUseCase;
import io.edpn.backend.trade.application.usecase.ReceiveSystemCoordinatesResponseUseCase;
import io.edpn.backend.trade.application.usecase.ReceiveSystemEliteIdResponseUseCase;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.domain.repository.CommodityMarketInfoRepository;
import io.edpn.backend.trade.domain.repository.CommodityRepository;
import io.edpn.backend.trade.domain.repository.LocateCommodityRepository;
import io.edpn.backend.trade.domain.repository.MarketDatumRepository;
import io.edpn.backend.trade.domain.repository.StationRepository;
import io.edpn.backend.trade.domain.repository.SystemRepository;
import io.edpn.backend.trade.domain.repository.ValidatedCommodityRepository;
import io.edpn.backend.trade.domain.service.RequestDataService;
import io.edpn.backend.trade.domain.usecase.FindCommodityMarketInfoUseCase;
import io.edpn.backend.trade.domain.usecase.FindCommodityUseCase;
import io.edpn.backend.trade.domain.usecase.LocateCommodityUseCase;
import io.edpn.backend.trade.domain.usecase.ReceiveCommodityMessageUseCase;
import io.edpn.backend.trade.domain.usecase.ReceiveDataRequestResponseUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration("TradeModuleUseCaseConfig")
public class UseCaseConfig {

    @Bean(name = "tradeFindBestCommodityPriceUseCase")
    public FindCommodityMarketInfoUseCase findCommodityMarketInfoCase(CommodityMarketInfoRepository commodityMarketInfoRepository) {
        return new DefaultFindCommodityMarketInfoUseCase(commodityMarketInfoRepository);
    }

    @Bean(name = "tradeReceiveCommodityMessageUseCase")
    public ReceiveCommodityMessageUseCase receiveCommodityMessageUseCase(CommodityRepository commodityRepository, SystemRepository systemRepository, StationRepository stationRepository, MarketDatumRepository marketDatumRepository, List<RequestDataService<Station>> stationRequestDataServices, List<RequestDataService<System>> systemRequestDataServices) {
        return new DefaultReceiveCommodityMessageUseCase(commodityRepository, systemRepository, stationRepository, marketDatumRepository, stationRequestDataServices, systemRequestDataServices);
    }

    @Bean(name = "tradeReceiveStationArrivalDistanceResponseUseCase")
    public ReceiveDataRequestResponseUseCase<StationArrivalDistanceResponse> receiveStationArrivalDistanceResponseUseCase(SystemRepository systemRepository, StationRepository stationRepository) {
        return new ReceiveStationArrivalDistanceResponseUseCase(systemRepository, stationRepository);
    }

    @Bean(name = "tradeReceiveStationMaxLandingPadSizeResponseUseCase")
    public ReceiveDataRequestResponseUseCase<StationMaxLandingPadSizeResponse> receiveStationMaxLandingPadSizeResponseUseCase(SystemRepository systemRepository, StationRepository stationRepository) {
        return new ReceiveStationMaxLandingPadSizeResponseUseCase(systemRepository, stationRepository);
    }

    @Bean(name = "tradeReceiveSystemCoordinatesResponseUseCase")
    public ReceiveDataRequestResponseUseCase<SystemCoordinatesResponse> receiveSystemCoordinatesResponseUseCase(SystemRepository systemRepository) {
        return new ReceiveSystemCoordinatesResponseUseCase(systemRepository);
    }

    @Bean(name = "tradeReceiveSystemEliteIdResponseUseCase")
    public ReceiveDataRequestResponseUseCase<SystemEliteIdResponse> receiveSystemEliteIdResponseUseCase(SystemRepository systemRepository) {
        return new ReceiveSystemEliteIdResponseUseCase(systemRepository);
    }

    @Bean(name = "tradeLocateCommodityUseCase")
    public LocateCommodityUseCase locateCommodityUseCase(LocateCommodityRepository locateCommodityRepository) {
        return new DefaultLocateCommodityUseCase(locateCommodityRepository);
    }

    @Bean(name = "tradeFindCommodityUseCase")
    public FindCommodityUseCase findCommodityUseCase(ValidatedCommodityRepository validatedCommodityRepository) {
        return new DefaultFindCommodityUseCase(validatedCommodityRepository);
    }
}
