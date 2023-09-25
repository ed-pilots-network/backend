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
import io.edpn.backend.trade.application.port.outgoing.station.LoadStationsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.station.UpdateStationPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.CleanUpObsoleteStationArrivalDistanceRequestsUseCase;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.CreateStationArrivalDistanceRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.DeleteStationArrivalDistanceRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.ExistsStationArrivalDistanceRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.LoadAllStationArrivalDistanceRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.RequestMissingStationArrivalDistanceUseCase;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.CleanUpObsoleteStationLandingPadSizeRequestsUseCase;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.CreateStationLandingPadSizeRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.DeleteStationLandingPadSizeRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.ExistsStationLandingPadSizeRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.LoadAllStationLandingPadSizeRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.RequestMissingStationLandingPadSizeUseCase;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.CleanUpObsoleteStationPlanetaryRequestsUseCase;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.CreateStationPlanetaryRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.DeleteStationPlanetaryRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.ExistsStationPlanetaryRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.LoadAllStationPlanetaryRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.RequestMissingStationPlanetaryUseCase;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.CleanUpObsoleteStationRequireOdysseyRequestsUseCase;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.CreateStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.DeleteStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.ExistsStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.LoadAllStationRequireOdysseyRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.RequestMissingStationRequireOdysseyUseCase;
import io.edpn.backend.trade.application.port.outgoing.system.LoadOrCreateSystemByNamePort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.system.UpdateSystemPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.CleanUpObsoleteSystemCoordinateRequestsUseCase;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.CreateSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.ExistsSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.LoadAllSystemCoordinateRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.CleanUpObsoleteSystemEliteIdRequestsUseCase;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.CreateSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.ExistsSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.LoadAllSystemEliteIdRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.RequestMissingSystemEliteIdUseCase;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadAllValidatedCommodityPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByNamePort;
import io.edpn.backend.trade.application.service.CleanUpObsoleteStationArrivalDistanceRequestsService;
import io.edpn.backend.trade.application.service.CleanUpObsoleteStationLandingPadSizeRequestsService;
import io.edpn.backend.trade.application.service.CleanUpObsoleteStationPlanetaryRequestsService;
import io.edpn.backend.trade.application.service.CleanUpObsoleteStationRequireOdysseyRequestsService;
import io.edpn.backend.trade.application.service.CleanUpObsoleteSystemCoordinateRequestsService;
import io.edpn.backend.trade.application.service.CleanUpObsoleteSystemEliteIdRequestsService;
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
import io.edpn.backend.trade.application.service.RequestMissingStationArrivalDistanceService;
import io.edpn.backend.trade.application.service.RequestMissingStationMaxLandingPadSizeService;
import io.edpn.backend.trade.application.service.RequestMissingStationPlanetaryService;
import io.edpn.backend.trade.application.service.RequestMissingStationRequireOdysseyService;
import io.edpn.backend.trade.application.service.RequestMissingSystemCoordinatesService;
import io.edpn.backend.trade.application.service.RequestMissingSystemEliteIdService;
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
            DeleteStationArrivalDistanceRequestPort deleteStationArrivalDistanceRequestPort,
            UpdateStationPort updateStationPort) {
        return new ReceiveStationArrivalDistanceResponseService(loadOrCreateSystemByNamePort, loadOrCreateBySystemAndStationNamePort, deleteStationArrivalDistanceRequestPort, updateStationPort);
    }

    @Bean(name = "tradeReceiveStationMaxLandingPadSizeResponseService")
    public ReceiveStationMaxLandingPadSizeResponseService receiveStationMaxLandingPadSizeResponseService(
            LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort,
            LoadOrCreateBySystemAndStationNamePort loadOrCreateBySystemAndStationNamePort,
            DeleteStationLandingPadSizeRequestPort deleteStationLandingPadSizeRequestPort,
            UpdateStationPort updateStationPort) {
        return new ReceiveStationMaxLandingPadSizeResponseService(loadOrCreateSystemByNamePort, loadOrCreateBySystemAndStationNamePort, deleteStationLandingPadSizeRequestPort, updateStationPort);
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
            DeleteStationRequireOdysseyRequestPort deleteStationRequireOdysseyRequestPort,
            UpdateStationPort updateStationPort) {
        return new ReceiveStationRequireOdysseyResponseService(loadOrCreateSystemByNamePort, loadOrCreateBySystemAndStationNamePort, deleteStationRequireOdysseyRequestPort, updateStationPort);
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
            ExistsStationArrivalDistanceRequestPort existsStationArrivalDistanceRequestPort,
            CreateStationArrivalDistanceRequestPort createStationArrivalDistanceRequestPort,
            ObjectMapper objectMapper,
            MessageMapper messageMapper) {
        return new RequestStationArrivalDistanceService(sendKafkaMessagePort, existsStationArrivalDistanceRequestPort, createStationArrivalDistanceRequestPort, objectMapper, messageMapper);
    }

    @Bean(name = "tradeRequestStationLandingPadSizeService")
    public RequestStationLandingPadSizeService requestStationLandingPadSizeService(
            SendKafkaMessagePort sendKafkaMessagePort,
            ExistsStationLandingPadSizeRequestPort existsStationLandingPadSizeRequestPort,
            CreateStationLandingPadSizeRequestPort createStationLandingPadSizeRequestPort,
            ObjectMapper objectMapper,
            MessageMapper messageMapper) {
        return new RequestStationLandingPadSizeService(sendKafkaMessagePort, existsStationLandingPadSizeRequestPort, createStationLandingPadSizeRequestPort, objectMapper, messageMapper);
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
            ExistsStationRequireOdysseyRequestPort existsStationRequireOdysseyRequestPort,
            CreateStationRequireOdysseyRequestPort createStationRequireOdysseyRequestPort,
            ObjectMapper objectMapper,
            MessageMapper messageMapper) {
        return new RequestStationRequireOdysseyService(sendKafkaMessagePort, existsStationRequireOdysseyRequestPort, createStationRequireOdysseyRequestPort, objectMapper, messageMapper);
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

    @Bean(name = "tradeRequestMissingStationRequireOdysseyUseCase")
    public RequestMissingStationRequireOdysseyUseCase requestMissingStationRequireOdysseyUseCase(
            LoadStationsByFilterPort loadStationsByFilterPort,
            CreateStationRequireOdysseyRequestPort createStationRequireOdysseyRequestPort,
            SendKafkaMessagePort sendKafkaMessagePort,
            @Qualifier("tradeRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("tradeThreadPoolTaskExecutor") Executor executor,
            ObjectMapper objectMapper,
            MessageMapper messageMapper
    ) {
        return new RequestMissingStationRequireOdysseyService(loadStationsByFilterPort, createStationRequireOdysseyRequestPort, sendKafkaMessagePort, retryTemplate, executor, objectMapper, messageMapper);
    }

    @Bean(name = "tradeRequestMissingStationLandingPadSizeUseCase")
    public RequestMissingStationLandingPadSizeUseCase requestMissingStationMaxLandingPadSizeService(
            LoadStationsByFilterPort loadStationsByFilterPort,
            CreateStationLandingPadSizeRequestPort createStationLandingPadSizeRequestPort,
            SendKafkaMessagePort sendKafkaMessagePort,
            @Qualifier("tradeRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("tradeThreadPoolTaskExecutor") Executor executor,
            ObjectMapper objectMapper,
            MessageMapper messageMapper
    ) {
        return new RequestMissingStationMaxLandingPadSizeService(loadStationsByFilterPort, createStationLandingPadSizeRequestPort, sendKafkaMessagePort, retryTemplate, executor, objectMapper, messageMapper);
    }

    @Bean(name = "tradeRequestMissingStationPlanetaryUseCase")
    public RequestMissingStationPlanetaryUseCase requestMissingStationPlanetaryUseCase(
            LoadStationsByFilterPort loadStationsByFilterPort,
            CreateStationPlanetaryRequestPort createStationPlanetaryRequestPort,
            SendKafkaMessagePort sendKafkaMessagePort,
            @Qualifier("tradeRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("tradeThreadPoolTaskExecutor") Executor executor,
            ObjectMapper objectMapper,
            MessageMapper messageMapper
    ) {
        return new RequestMissingStationPlanetaryService(loadStationsByFilterPort, createStationPlanetaryRequestPort, sendKafkaMessagePort, retryTemplate, executor, objectMapper, messageMapper);
    }

    @Bean(name = "tradeRequestMissingSystemEliteIdUseCase")
    public RequestMissingSystemEliteIdUseCase requestMissingSystemEliteIdUseCase(
            LoadSystemsByFilterPort loadSystemsByFilterPort,
            CreateSystemEliteIdRequestPort createSystemEliteIdRequestPort,
            SendKafkaMessagePort sendKafkaMessagePort,
            @Qualifier("tradeRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("tradeThreadPoolTaskExecutor") Executor executor,
            ObjectMapper objectMapper,
            MessageMapper messageMapper
    ) {
        return new RequestMissingSystemEliteIdService(loadSystemsByFilterPort, createSystemEliteIdRequestPort, sendKafkaMessagePort, retryTemplate, executor, objectMapper, messageMapper);
    }

    @Bean(name = "tradeRequestMissingArrivalDistanceOdysseyUseCase")
    public RequestMissingStationArrivalDistanceUseCase requestMissingStationArrivalDistanceUseCase(
            LoadStationsByFilterPort loadStationsByFilterPort,
            CreateStationArrivalDistanceRequestPort createStationArrivalDistanceRequestPort,
            SendKafkaMessagePort sendKafkaMessagePort,
            @Qualifier("tradeRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("tradeThreadPoolTaskExecutor") Executor executor,
            ObjectMapper objectMapper,
            MessageMapper messageMapper
    ) {
        return new RequestMissingStationArrivalDistanceService(loadStationsByFilterPort, createStationArrivalDistanceRequestPort, sendKafkaMessagePort, retryTemplate, executor, objectMapper, messageMapper);
    }

    @Bean(name = "tradeCleanUpObsoleteStationArrivalDistanceRequestsUseCase")
    public CleanUpObsoleteStationArrivalDistanceRequestsUseCase cleanUpObsoleteStationArrivalDistanceRequestsUseCase(
            LoadStationsByFilterPort loadStationsByFilterPort,
            LoadAllStationArrivalDistanceRequestsPort loadAllStationArrivalDistanceRequestsPort,
            DeleteStationArrivalDistanceRequestPort deleteStationArrivalDistanceRequestPort) {
        return new CleanUpObsoleteStationArrivalDistanceRequestsService(loadStationsByFilterPort, loadAllStationArrivalDistanceRequestsPort, deleteStationArrivalDistanceRequestPort);
    }

    @Bean(name = "tradeCleanUpObsoleteStationLandingPadSizeRequestsUseCase")
    public CleanUpObsoleteStationLandingPadSizeRequestsUseCase cleanUpObsoleteStationLandingPadSizeRequestsUseCase(
            LoadStationsByFilterPort loadStationsByFilterPort,
            LoadAllStationLandingPadSizeRequestsPort loadAllStationLandingPadSizeRequestsPort,
            DeleteStationLandingPadSizeRequestPort deleteStationLandingPadSizeRequestPort) {
        return new CleanUpObsoleteStationLandingPadSizeRequestsService(loadStationsByFilterPort, loadAllStationLandingPadSizeRequestsPort, deleteStationLandingPadSizeRequestPort);
    }

    @Bean(name = "tradeCleanUpObsoleteStationPlanetaryRequestsUseCase")
    public CleanUpObsoleteStationPlanetaryRequestsUseCase cleanUpObsoleteStationPlanetaryRequestsUseCase(
            LoadStationsByFilterPort loadStationsByFilterPort,
            LoadAllStationPlanetaryRequestsPort loadAllStationPlanetaryRequestsPort,
            DeleteStationPlanetaryRequestPort deleteStationPlanetaryRequestPort) {
        return new CleanUpObsoleteStationPlanetaryRequestsService(loadStationsByFilterPort, loadAllStationPlanetaryRequestsPort, deleteStationPlanetaryRequestPort);
    }

    @Bean(name = "tradeCleanUpObsoleteStationRequireOdysseyRequestsUseCase")
    public CleanUpObsoleteStationRequireOdysseyRequestsUseCase cleanUpObsoleteStationRequireOdysseyRequestsUseCase(
            LoadStationsByFilterPort loadStationsByFilterPort,
            LoadAllStationRequireOdysseyRequestsPort loadAllStationRequireOdysseyRequestsPort,
            DeleteStationRequireOdysseyRequestPort deleteStationRequireOdysseyRequestPort) {
        return new CleanUpObsoleteStationRequireOdysseyRequestsService(loadStationsByFilterPort, loadAllStationRequireOdysseyRequestsPort, deleteStationRequireOdysseyRequestPort);
    }

    @Bean(name = "tradeCleanUpObsoleteSystemCoordinateRequestsUseCase")
    public CleanUpObsoleteSystemCoordinateRequestsUseCase cleanUpObsoleteSystemCoordinateRequestsUseCase(
            LoadSystemsByFilterPort loadSystemsByFilterPort,
            LoadAllSystemCoordinateRequestsPort loadAllSystemCoordinateRequestsPort,
            DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort) {
        return new CleanUpObsoleteSystemCoordinateRequestsService(loadSystemsByFilterPort, loadAllSystemCoordinateRequestsPort, deleteSystemCoordinateRequestPort);
    }

    @Bean(name = "tradeCleanUpObsoleteSystemEliteIdRequestsUseCase")
    public CleanUpObsoleteSystemEliteIdRequestsUseCase cleanUpObsoleteSystemEliteIdRequestsUseCase(
            LoadSystemsByFilterPort loadSystemsByFilterPort,
            LoadAllSystemEliteIdRequestsPort loadAllSystemEliteIdRequestsPort,
            DeleteSystemEliteIdRequestPort deleteSystemEliteIdRequestPort) {
        return new CleanUpObsoleteSystemEliteIdRequestsService(loadSystemsByFilterPort, loadAllSystemEliteIdRequestsPort, deleteSystemEliteIdRequestPort);
    }
}
