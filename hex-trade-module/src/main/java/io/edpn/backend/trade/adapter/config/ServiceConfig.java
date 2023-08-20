package io.edpn.backend.trade.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.trade.application.dto.web.filter.mapper.FindCommodityFilterDtoMapper;
import io.edpn.backend.trade.application.dto.web.filter.mapper.LocateCommodityFilterDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.CommodityMarketInfoDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.LocateCommodityDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.MessageMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.ValidatedCommodityDtoMapper;
import io.edpn.backend.trade.application.port.outgoing.commoditymarketinfo.GetFullCommodityMarketInfoPort;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.locatecommodity.LocateCommodityByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.station.LoadOrCreateBySystemAndStationNamePort;
import io.edpn.backend.trade.application.port.outgoing.station.UpdateStationPort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadOrCreateSystemByNamePort;
import io.edpn.backend.trade.application.port.outgoing.system.UpdateSystemPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadAllValidatedCommodityPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByNamePort;
import io.edpn.backend.trade.application.service.FindCommodityMarketInfoService;
import io.edpn.backend.trade.application.service.FindCommodityService;
import io.edpn.backend.trade.application.service.LocateCommodityService;
import io.edpn.backend.trade.application.service.ReceiveStationArrivalDistanceResponseService;
import io.edpn.backend.trade.application.service.ReceiveStationMaxLandingPadSizeResponseService;
import io.edpn.backend.trade.application.service.ReceiveStationPlanetaryResponseService;
import io.edpn.backend.trade.application.service.ReceiveStationRequireOdysseyResponseService;
import io.edpn.backend.trade.application.service.ReceiveSystemCoordinatesResponseService;
import io.edpn.backend.trade.application.service.ReceiveSystemEliteIdResponseService;
import io.edpn.backend.trade.application.service.RequestStationArrivalDistanceService;
import io.edpn.backend.trade.application.service.RequestStationLandingPadSizeService;
import io.edpn.backend.trade.application.service.RequestStationPlanetaryService;
import io.edpn.backend.trade.application.service.RequestStationRequireOdysseyService;
import io.edpn.backend.trade.application.service.RequestSystemCoordinatesService;
import io.edpn.backend.trade.application.service.RequestSystemEliteIdService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("TradeServiceConfig")
public class ServiceConfig {
    
    @Bean(name = "tradeFindCommodityService")
    public FindCommodityService findCommodityService(
            LoadAllValidatedCommodityPort loadAllValidatedCommodityPort,
            LoadValidatedCommodityByNamePort loadValidatedCommodityByNamePort,
            LoadValidatedCommodityByFilterPort loadValidatedCommodityByFilterPort,
            ValidatedCommodityDtoMapper validatedCommodityDTOMapper,
            FindCommodityFilterDtoMapper findCommodityFilterDtoMapper) {
        return new FindCommodityService(loadAllValidatedCommodityPort, loadValidatedCommodityByNamePort, loadValidatedCommodityByFilterPort, validatedCommodityDTOMapper, findCommodityFilterDtoMapper);
    }
    
    @Bean(name = "tradeFindCommodityMarketInfoService")
    public FindCommodityMarketInfoService findCommodityMarketInfoService(
            GetFullCommodityMarketInfoPort commodityMarketInfoPort,
            CommodityMarketInfoDtoMapper commodityMarketInfoDtoMapper) {
        return new FindCommodityMarketInfoService(commodityMarketInfoPort, commodityMarketInfoDtoMapper);
    }
    
    @Bean(name = "tradeLocateCommodityService")
    public LocateCommodityService locateCommodityService(
            LocateCommodityByFilterPort locateCommodityByFilterPort,
            LocateCommodityFilterDtoMapper locateCommodityFilterDtoMapper,
            LocateCommodityDtoMapper locateCommodityDtoMapper) {
        return new LocateCommodityService(locateCommodityByFilterPort, locateCommodityFilterDtoMapper, locateCommodityDtoMapper);
    }
    
    @Bean(name = "tradeReceiveStationArrivalDistanceResponseService")
    public ReceiveStationArrivalDistanceResponseService receiveStationArrivalDistanceResponseService(
            LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort,
            LoadOrCreateBySystemAndStationNamePort loadOrCreateBySystemAndStationNamePort,
            UpdateStationPort updateStationPort) {
        return new ReceiveStationArrivalDistanceResponseService(loadOrCreateSystemByNamePort, loadOrCreateBySystemAndStationNamePort, updateStationPort);
    }
    
    @Bean(name = "tradeReceiveStationMaxLandingPadSizeResponseService")
    public ReceiveStationMaxLandingPadSizeResponseService receiveStationMaxLandingPadSizeResponseService(
            LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort,
            LoadOrCreateBySystemAndStationNamePort loadOrCreateBySystemAndStationNamePort,
            UpdateStationPort updateStationPort) {
        return new ReceiveStationMaxLandingPadSizeResponseService(loadOrCreateSystemByNamePort, loadOrCreateBySystemAndStationNamePort, updateStationPort);
    }
    
    @Bean(name = "tradeReceiveStationPlanetaryResponseService")
    public ReceiveStationPlanetaryResponseService receiveStationPlanetaryResponseService(
            LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort,
            LoadOrCreateBySystemAndStationNamePort loadOrCreateBySystemAndStationNamePort,
            UpdateStationPort updateStationPort) {
        return new ReceiveStationPlanetaryResponseService(loadOrCreateSystemByNamePort, loadOrCreateBySystemAndStationNamePort, updateStationPort);
    }
    
    @Bean(name = "tradeReceiveStationRequireOdysseyResponseService")
    public ReceiveStationRequireOdysseyResponseService receiveStationRequireOdysseyResponseService(
            LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort,
            LoadOrCreateBySystemAndStationNamePort loadOrCreateBySystemAndStationNamePort,
            UpdateStationPort updateStationPort) {
        return new ReceiveStationRequireOdysseyResponseService(loadOrCreateSystemByNamePort, loadOrCreateBySystemAndStationNamePort, updateStationPort);
    }
    
    @Bean(name = "tradeReceiveSystemCoordinatesResponseService")
    public ReceiveSystemCoordinatesResponseService receiveSystemCoordinatesResponseService(
            LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort,
            UpdateSystemPort updateSystemPort) {
        return new ReceiveSystemCoordinatesResponseService(loadOrCreateSystemByNamePort, updateSystemPort);
    }
    
    @Bean(name = "tradeReceiveSystemEliteIdResponseService")
    public ReceiveSystemEliteIdResponseService receiveSystemEliteIdResponseService(
            LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort,
            UpdateSystemPort updateSystemPort) {
        return new ReceiveSystemEliteIdResponseService(loadOrCreateSystemByNamePort, updateSystemPort);
    }
    
    @Bean(name = "requestStationArrivalDistanceService")
    public RequestStationArrivalDistanceService requestStationArrivalDistanceService(
            SendKafkaMessagePort sendKafkaMessagePort,
            ObjectMapper objectMapper,
            MessageMapper messageMapper) {
        return new RequestStationArrivalDistanceService(sendKafkaMessagePort, objectMapper, messageMapper);
    }
    
    @Bean(name = "requestStationLandingPadSizeService")
    public RequestStationLandingPadSizeService requestStationLandingPadSizeService(
            SendKafkaMessagePort sendKafkaMessagePort,
            ObjectMapper objectMapper,
            MessageMapper messageMapper) {
        return new RequestStationLandingPadSizeService(sendKafkaMessagePort, objectMapper, messageMapper);
    }
    
    @Bean(name = "requestStationPlanetaryService")
    public RequestStationPlanetaryService requestStationPlanetaryService(
            SendKafkaMessagePort sendKafkaMessagePort,
            ObjectMapper objectMapper,
            MessageMapper messageMapper) {
        return new RequestStationPlanetaryService(sendKafkaMessagePort, objectMapper, messageMapper);
    }
    
    @Bean(name = "requestStationRequireOdysseyService")
    public RequestStationRequireOdysseyService requestStationRequireOdysseyService(
            SendKafkaMessagePort sendKafkaMessagePort,
            ObjectMapper objectMapper,
            MessageMapper messageMapper) {
        return new RequestStationRequireOdysseyService(sendKafkaMessagePort, objectMapper, messageMapper);
    }
    
    @Bean(name = "requestSystemCoordinateService")
    public RequestSystemCoordinatesService requestSystemCoordinatesService(
            SendKafkaMessagePort sendKafkaMessagePort,
            ObjectMapper objectMapper,
            MessageMapper messageMapper) {
        return new RequestSystemCoordinatesService(sendKafkaMessagePort, objectMapper, messageMapper);
    }
    
    @Bean(name = "requestSystemEliteIdService")
    public RequestSystemEliteIdService requestSystemEliteIdService(
            SendKafkaMessagePort sendKafkaMessagePort,
            ObjectMapper objectMapper,
            MessageMapper messageMapper) {
        return new RequestSystemEliteIdService(sendKafkaMessagePort, objectMapper, messageMapper);
    }
    
}
