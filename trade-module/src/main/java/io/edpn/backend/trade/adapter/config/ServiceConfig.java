package io.edpn.backend.trade.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.dto.web.filter.mapper.FindCommodityFilterDtoMapper;
import io.edpn.backend.trade.application.dto.web.filter.mapper.LocateCommodityFilterDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.CommodityMarketInfoDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.LocateCommodityDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.MessageMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.ValidatedCommodityDtoMapper;
import io.edpn.backend.trade.application.port.incomming.kafka.RequestDataUseCase;
import io.edpn.backend.trade.application.port.outgoing.commodity.LoadOrCreateCommodityByNamePort;
import io.edpn.backend.trade.application.port.outgoing.commoditymarketinfo.GetFullCommodityMarketInfoPort;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.locatecommodity.LocateCommodityByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.marketdatum.ExistsByStationNameAndSystemNameAndTimestampPort;
import io.edpn.backend.trade.application.port.outgoing.station.LoadOrCreateBySystemAndStationNamePort;
import io.edpn.backend.trade.application.port.outgoing.station.UpdateStationPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.CreateStationPlanetaryRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.DeleteStationPlanetaryRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.ExistsStationPlanetaryRequestPort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadOrCreateSystemByNamePort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.system.UpdateSystemPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.CreateSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.ExistsSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.CreateSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.ExistsSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadAllValidatedCommodityPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByNamePort;
import io.edpn.backend.trade.application.service.FindCommodityMarketInfoService;
import io.edpn.backend.trade.application.service.FindCommodityService;
import io.edpn.backend.trade.application.service.LocateCommodityService;
import io.edpn.backend.trade.application.service.ReceiveCommodityMessageService;
import io.edpn.backend.trade.application.service.ReceiveStationArrivalDistanceResponseService;
import io.edpn.backend.trade.application.service.ReceiveStationMaxLandingPadSizeResponseService;
import io.edpn.backend.trade.application.service.ReceiveStationPlanetaryResponseService;
import io.edpn.backend.trade.application.service.ReceiveStationRequireOdysseyResponseService;
import io.edpn.backend.trade.application.service.ReceiveSystemCoordinatesResponseService;
import io.edpn.backend.trade.application.service.ReceiveSystemEliteIdResponseService;
import io.edpn.backend.trade.application.service.RequestMissingSystemCoordinatesService;
import io.edpn.backend.trade.application.service.RequestStationArrivalDistanceService;
import io.edpn.backend.trade.application.service.RequestStationLandingPadSizeService;
import io.edpn.backend.trade.application.service.RequestStationPlanetaryService;
import io.edpn.backend.trade.application.service.RequestStationRequireOdysseyService;
import io.edpn.backend.trade.application.service.RequestSystemCoordinatesService;
import io.edpn.backend.trade.application.service.RequestSystemEliteIdService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

import java.util.List;
import java.util.concurrent.Executor;

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

    @Bean(name = "tradeRecieveCommodityMessageUsecase")
    public ReceiveCommodityMessageService receiveCommodityMessageService(
            ExistsByStationNameAndSystemNameAndTimestampPort existsByStationNameAndSystemNameAndTimestamp,
            LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort,
            LoadOrCreateBySystemAndStationNamePort loadOrCreateBySystemAndStationNamePort,
            LoadOrCreateCommodityByNamePort loadOrCreateCommodityByNamePort,
            UpdateStationPort updateStationPort,
            List<RequestDataUseCase<Station>> stationRequestDataServices,
            List<RequestDataUseCase<System>> systemRequestDataService) {
        return new ReceiveCommodityMessageService(existsByStationNameAndSystemNameAndTimestamp, loadOrCreateSystemByNamePort, loadOrCreateBySystemAndStationNamePort, loadOrCreateCommodityByNamePort, updateStationPort, stationRequestDataServices, systemRequestDataService);
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
            DeleteStationPlanetaryRequestPort deleteStationPlanetaryRequestPort,
            UpdateStationPort updateStationPort) {
        return new ReceiveStationPlanetaryResponseService(loadOrCreateSystemByNamePort, loadOrCreateBySystemAndStationNamePort, deleteStationPlanetaryRequestPort, updateStationPort);
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
            DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort,
            UpdateSystemPort updateSystemPort) {
        return new ReceiveSystemCoordinatesResponseService(loadOrCreateSystemByNamePort, deleteSystemCoordinateRequestPort, updateSystemPort);
    }

    @Bean(name = "tradeReceiveSystemEliteIdResponseService")
    public ReceiveSystemEliteIdResponseService receiveSystemEliteIdResponseService(
            LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort,
            DeleteSystemEliteIdRequestPort deleteSystemEliteIdRequestPort,
            UpdateSystemPort updateSystemPort) {
        return new ReceiveSystemEliteIdResponseService(loadOrCreateSystemByNamePort, deleteSystemEliteIdRequestPort, updateSystemPort);
    }

    @Bean(name = "tradeRequestStationArrivalDistanceService")
    public RequestStationArrivalDistanceService requestStationArrivalDistanceService(
            SendKafkaMessagePort sendKafkaMessagePort,
            ObjectMapper objectMapper,
            MessageMapper messageMapper) {
        return new RequestStationArrivalDistanceService(sendKafkaMessagePort, objectMapper, messageMapper);
    }

    @Bean(name = "tradeRequestStationLandingPadSizeService")
    public RequestStationLandingPadSizeService requestStationLandingPadSizeService(
            SendKafkaMessagePort sendKafkaMessagePort,
            ObjectMapper objectMapper,
            MessageMapper messageMapper) {
        return new RequestStationLandingPadSizeService(sendKafkaMessagePort, objectMapper, messageMapper);
    }

    @Bean(name = "tradeRequestStationPlanetaryService")
    public RequestStationPlanetaryService requestStationPlanetaryService(
            SendKafkaMessagePort sendKafkaMessagePort,
            ExistsStationPlanetaryRequestPort existsStationPlanetaryRequestPort,
            CreateStationPlanetaryRequestPort createStationPlanetaryRequestPort,
            ObjectMapper objectMapper,
            MessageMapper messageMapper) {
        return new RequestStationPlanetaryService(sendKafkaMessagePort, existsStationPlanetaryRequestPort, createStationPlanetaryRequestPort, objectMapper, messageMapper);
    }

    @Bean(name = "tradeRequestStationRequireOdysseyService")
    public RequestStationRequireOdysseyService requestStationRequireOdysseyService(
            SendKafkaMessagePort sendKafkaMessagePort,
            ObjectMapper objectMapper,
            MessageMapper messageMapper) {
        return new RequestStationRequireOdysseyService(sendKafkaMessagePort, objectMapper, messageMapper);
    }

    @Bean(name = "tradeRequestSystemCoordinateService")
    public RequestSystemCoordinatesService requestSystemCoordinatesService(
            SendKafkaMessagePort sendKafkaMessagePort,
            ExistsSystemCoordinateRequestPort existsSystemCoordinateRequestPort,
            CreateSystemCoordinateRequestPort createSystemCoordinateRequestPort,
            ObjectMapper objectMapper,
            MessageMapper messageMapper) {
        return new RequestSystemCoordinatesService(sendKafkaMessagePort, existsSystemCoordinateRequestPort, createSystemCoordinateRequestPort, objectMapper, messageMapper);
    }

    @Bean(name = "tradeRequestSystemEliteIdService")
    public RequestSystemEliteIdService requestSystemEliteIdService(
            SendKafkaMessagePort sendKafkaMessagePort,
            ExistsSystemEliteIdRequestPort existsSystemEliteIdRequestPort,
            CreateSystemEliteIdRequestPort createSystemEliteIdRequestPort,
            ObjectMapper objectMapper,
            MessageMapper messageMapper) {
        return new RequestSystemEliteIdService(sendKafkaMessagePort, existsSystemEliteIdRequestPort, createSystemEliteIdRequestPort, objectMapper, messageMapper);
    }

    @Bean(name = "tradeRequestMissingSystemCoordinatesService")
    public RequestMissingSystemCoordinatesService requestMissingSystemCoordinatesUseCase(
            LoadSystemsByFilterPort loadSystemsByFilterPort,
            CreateSystemCoordinateRequestPort createSystemCoordinateRequestPort,
            SendKafkaMessagePort sendKafkaMessagePort,
            @Qualifier("tradeRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("tradeThreadPoolTaskExecutor") Executor executor,
            ObjectMapper objectMapper,
            MessageMapper messageMapper
    ) {
        return new RequestMissingSystemCoordinatesService(loadSystemsByFilterPort, createSystemCoordinateRequestPort, sendKafkaMessagePort, retryTemplate, executor, objectMapper, messageMapper);
    }
}
