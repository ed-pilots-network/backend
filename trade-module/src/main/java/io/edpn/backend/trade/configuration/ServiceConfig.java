package io.edpn.backend.trade.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.trade.application.mappers.CommodityMarketInfoResponseMapper;
import io.edpn.backend.trade.application.mappers.FindCommodityMapper;
import io.edpn.backend.trade.application.service.*;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.domain.repository.RequestDataMessageRepository;
import io.edpn.backend.trade.domain.service.BestCommodityPriceService;
import io.edpn.backend.trade.domain.service.FindCommodityService;
import io.edpn.backend.trade.domain.service.RequestDataService;
import io.edpn.backend.trade.domain.usecase.FindCommodityMarketInfoUseCase;
import io.edpn.backend.trade.domain.usecase.FindCommodityUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("TradeModuleServiceConfig")
public class ServiceConfig {

    @Bean(name = "TradeModuleBestCommodityPriceService")
    public BestCommodityPriceService bestCommodityPriceController(FindCommodityMarketInfoUseCase findCommodityMarketInfoUseCase, CommodityMarketInfoResponseMapper commodityMarketInfoResponseMapper) {
        return new DefaultBestCommodityPriceService(findCommodityMarketInfoUseCase, commodityMarketInfoResponseMapper);
    }

    @Bean(name = "TradeModuleRequestStationArrivalDistanceService")
    public RequestDataService<Station> requestStationArrivalDistanceService(RequestDataMessageRepository requestDataMessageRepository, ObjectMapper objectMapper) {
        return new RequestStationArrivalDistanceService(requestDataMessageRepository, objectMapper);
    }

    @Bean(name = "TradeModuleRequestStationLandingPadSizeService")
    public RequestDataService<Station> requestStationLandingPadSizeService(RequestDataMessageRepository requestDataMessageRepository, ObjectMapper objectMapper) {
        return new RequestStationLandingPadSizeService(requestDataMessageRepository, objectMapper);
    }

    @Bean(name = "TradeModuleRequestSystemCoordinatesService")
    public RequestDataService<System> requestSystemCoordinatesService(RequestDataMessageRepository requestDataMessageRepository, ObjectMapper objectMapper) {
        return new RequestSystemCoordinatesService(requestDataMessageRepository, objectMapper);
    }

    @Bean(name = "TradeModuleRequestSystemEliteIdService")
    public RequestDataService<System> requestSystemEliteIdService(RequestDataMessageRepository requestDataMessageRepository, ObjectMapper objectMapper) {
        return new RequestSystemEliteIdService(requestDataMessageRepository, objectMapper);
    }
    
    @Bean(name = "TradeModuleFindCommodityService")
    public FindCommodityService findCommodityService(FindCommodityUseCase findCommodityUseCase, FindCommodityMapper findCommodityMapper){
        return new DefaultFindCommodityService(findCommodityUseCase, findCommodityMapper);
    }
}
