package io.edpn.backend.commodityfinder.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.commodityfinder.application.mappers.CommodityMarketInfoResponseMapper;
import io.edpn.backend.commodityfinder.application.service.DefaultBestCommodityPriceService;
import io.edpn.backend.commodityfinder.application.service.RequestStationArrivalDistanceService;
import io.edpn.backend.commodityfinder.application.service.RequestStationLandingPadSizeService;
import io.edpn.backend.commodityfinder.application.service.RequestSystemCoordinatesService;
import io.edpn.backend.commodityfinder.application.service.RequestSystemEliteIdService;
import io.edpn.backend.commodityfinder.domain.model.Station;
import io.edpn.backend.commodityfinder.domain.model.System;
import io.edpn.backend.commodityfinder.domain.repository.RequestDataMessageRepository;
import io.edpn.backend.commodityfinder.domain.service.BestCommodityPriceService;
import io.edpn.backend.commodityfinder.domain.service.RequestDataService;
import io.edpn.backend.commodityfinder.domain.usecase.FindCommodityMarketInfoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("CommodityFinderServiceConfig")
public class ServiceConfig {

    @Bean(name = "CommodityFinderBestCommodityPriceService")
    public BestCommodityPriceService bestCommodityPriceController(FindCommodityMarketInfoUseCase findCommodityMarketInfoUseCase, CommodityMarketInfoResponseMapper commodityMarketInfoResponseMapper) {
        return new DefaultBestCommodityPriceService(findCommodityMarketInfoUseCase, commodityMarketInfoResponseMapper);
    }

    @Bean(name = "CommodityFinderRequestStationArrivalDistanceService")
    public RequestDataService<Station> requestStationArrivalDistanceService(RequestDataMessageRepository requestDataMessageRepository, ObjectMapper objectMapper) {
        return new RequestStationArrivalDistanceService(requestDataMessageRepository, objectMapper);
    }

    @Bean(name = "CommodityFinderRequestStationLandingPadSizeService")
    public RequestDataService<Station> requestStationLandingPadSizeService(RequestDataMessageRepository requestDataMessageRepository, ObjectMapper objectMapper) {
        return new RequestStationLandingPadSizeService(requestDataMessageRepository, objectMapper);
    }

    @Bean(name = "CommodityFinderRequestSystemCoordinatesService")
    public RequestDataService<System> requestSystemCoordinatesService(RequestDataMessageRepository requestDataMessageRepository, ObjectMapper objectMapper) {
        return new RequestSystemCoordinatesService(requestDataMessageRepository, objectMapper);
    }

    @Bean(name = "CommodityFinderRequestSystemEliteIdService")
    public RequestDataService<System> requestSystemEliteIdService(RequestDataMessageRepository requestDataMessageRepository, ObjectMapper objectMapper) {
        return new RequestSystemEliteIdService(requestDataMessageRepository, objectMapper);
    }
}
