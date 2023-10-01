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
import io.edpn.backend.trade.application.port.outgoing.commodity.CreateOrLoadCommodityPort;
import io.edpn.backend.trade.application.port.outgoing.commoditymarketinfo.GetFullCommodityMarketInfoPort;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.locatecommodity.LocateCommodityByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.marketdatum.CreateOrUpdateOnConflictWhenNewerLatestMarketDatumPort;
import io.edpn.backend.trade.application.port.outgoing.marketdatum.CreateWhenNotExistsMarketDatumPort;
import io.edpn.backend.trade.application.port.outgoing.station.CreateOrLoadStationPort;
import io.edpn.backend.trade.application.port.outgoing.station.LoadStationsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.station.UpdateStationPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.CreateStationArrivalDistanceRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.DeleteStationArrivalDistanceRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.ExistsStationArrivalDistanceRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.LoadAllStationArrivalDistanceRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.CreateStationLandingPadSizeRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.DeleteStationLandingPadSizeRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.ExistsStationLandingPadSizeRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.LoadAllStationLandingPadSizeRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.CreateStationPlanetaryRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.DeleteStationPlanetaryRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.ExistsStationPlanetaryRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.LoadAllStationPlanetaryRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.CreateStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.DeleteStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.ExistsStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.LoadAllStationRequireOdysseyRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.system.CreateOrLoadSystemPort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.system.UpdateSystemPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.CreateSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.ExistsSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.LoadAllSystemCoordinateRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.CreateSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.ExistsSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.LoadAllSystemEliteIdRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadAllValidatedCommodityPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByNamePort;
import io.edpn.backend.trade.application.service.FindCommodityMarketInfoService;
import io.edpn.backend.trade.application.service.FindCommodityService;
import io.edpn.backend.trade.application.service.LocateCommodityService;
import io.edpn.backend.trade.application.service.ReceiveCommodityMessageService;
import io.edpn.backend.trade.application.service.StationArrivalDistanceInterModuleCommunicationService;
import io.edpn.backend.trade.application.service.StationLandingPadSizeInterModuleCommunicationService;
import io.edpn.backend.trade.application.service.StationPlanetaryInterModuleCommunicationService;
import io.edpn.backend.trade.application.service.StationRequireOdysseyInterModuleCommunicationService;
import io.edpn.backend.trade.application.service.SystemCoordinateInterModuleCommunicationService;
import io.edpn.backend.trade.application.service.SystemEliteIdInterModuleCommunicationService;
import io.edpn.backend.util.IdGenerator;
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
            @Qualifier("tradeIdGenerator") IdGenerator idGenerator,
            CreateOrLoadSystemPort loadOrCreateSystemByNamePort,
            CreateOrLoadStationPort loadOrCreateBySystemAndStationNamePort,
            CreateOrLoadCommodityPort loadOrCreateCommodityByNamePort,
            CreateWhenNotExistsMarketDatumPort createWhenNotExistsMarketDatumPort,
            CreateOrUpdateOnConflictWhenNewerLatestMarketDatumPort createOrUpdateOnConflictWhenNewerLatestMarketDatumPort,
            UpdateStationPort updateStationPort,
            List<RequestDataUseCase<Station>> stationRequestDataServices,
            List<RequestDataUseCase<System>> systemRequestDataService) {
        return new ReceiveCommodityMessageService(idGenerator, loadOrCreateSystemByNamePort, loadOrCreateBySystemAndStationNamePort, loadOrCreateCommodityByNamePort, createWhenNotExistsMarketDatumPort, createOrUpdateOnConflictWhenNewerLatestMarketDatumPort, updateStationPort, stationRequestDataServices, systemRequestDataService);
    }


    @Bean(name = "tradeStationArrivalDistanceInterModuleCommunicationService")
    public StationArrivalDistanceInterModuleCommunicationService stationArrivalDistanceInterModuleCommunicationService(
            @Qualifier("tradeIdGenerator") IdGenerator idGenerator,
            LoadStationsByFilterPort loadStationsByFilterPort,
            LoadAllStationArrivalDistanceRequestsPort loadAllStationArrivalDistanceRequestsPort,
            CreateOrLoadSystemPort createOrLoadSystemPort,
            CreateOrLoadStationPort createOrLoadStationPort,
            ExistsStationArrivalDistanceRequestPort existsStationArrivalDistanceRequestPort,
            CreateStationArrivalDistanceRequestPort createStationArrivalDistanceRequestPort,
            DeleteStationArrivalDistanceRequestPort deleteStationArrivalDistanceRequestPort,
            UpdateStationPort updateStationPort,
            SendKafkaMessagePort sendKafkaMessagePort,
            @Qualifier("tradeRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("tradeForkJoinPool") Executor executor,
            ObjectMapper objectMapper,
            MessageMapper messageMapper) {
        return new StationArrivalDistanceInterModuleCommunicationService(
                idGenerator,
                loadStationsByFilterPort,
                loadAllStationArrivalDistanceRequestsPort,
                createOrLoadSystemPort,
                createOrLoadStationPort,
                existsStationArrivalDistanceRequestPort,
                createStationArrivalDistanceRequestPort,
                deleteStationArrivalDistanceRequestPort,
                updateStationPort,
                sendKafkaMessagePort,
                retryTemplate,
                executor,
                objectMapper,
                messageMapper);
    }

    @Bean(name = "tradeSystemCoordinateInterModuleCommunicationService")
    public SystemCoordinateInterModuleCommunicationService systemCoordinateInterModuleCommunicationService(
            @Qualifier("tradeIdGenerator") IdGenerator idGenerator,
            LoadSystemsByFilterPort loadSystemsByFilterPort,
            LoadAllSystemCoordinateRequestsPort loadAllSystemCoordinateRequestsPort,
            CreateOrLoadSystemPort createOrLoadSystemPort,
            ExistsSystemCoordinateRequestPort existsSystemCoordinateRequestPort,
            CreateSystemCoordinateRequestPort createSystemCoordinateRequestPort,
            DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort,
            UpdateSystemPort updateSystemPort,
            SendKafkaMessagePort sendKafkaMessagePort,
            @Qualifier("tradeRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("tradeForkJoinPool") Executor executor,
            ObjectMapper objectMapper,
            MessageMapper messageMapper
    ) {
        return new SystemCoordinateInterModuleCommunicationService(
                idGenerator,
                loadSystemsByFilterPort,
                loadAllSystemCoordinateRequestsPort,
                createOrLoadSystemPort,
                existsSystemCoordinateRequestPort,
                createSystemCoordinateRequestPort,
                deleteSystemCoordinateRequestPort,
                updateSystemPort,
                sendKafkaMessagePort,
                retryTemplate,
                executor,
                objectMapper,
                messageMapper
        );
    }

    @Bean(name = "tradeStationLandingPadSizeInterModuleCommunicationService")
    public StationLandingPadSizeInterModuleCommunicationService stationLandingPadSizeInterModuleCommunicationService(
            @Qualifier("tradeIdGenerator") IdGenerator idGenerator,
            LoadStationsByFilterPort loadStationsByFilterPort,
            LoadAllStationLandingPadSizeRequestsPort loadAllStationLandingPadSizeRequestsPort,
            CreateOrLoadSystemPort createOrLoadSystemPort,
            CreateOrLoadStationPort createOrLoadStationPort,
            ExistsStationLandingPadSizeRequestPort existsStationLandingPadSizeRequestPort,
            CreateStationLandingPadSizeRequestPort createStationLandingPadSizeRequestPort,
            DeleteStationLandingPadSizeRequestPort deleteStationLandingPadSizeRequestPort,
            UpdateStationPort updateStationPort,
            SendKafkaMessagePort sendKafkaMessagePort,
            @Qualifier("tradeRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("tradeForkJoinPool") Executor executor,
            ObjectMapper objectMapper,
            MessageMapper messageMapper
    ) {
        return new StationLandingPadSizeInterModuleCommunicationService(
                idGenerator,
                loadStationsByFilterPort,
                loadAllStationLandingPadSizeRequestsPort,
                createOrLoadSystemPort,
                createOrLoadStationPort,
                existsStationLandingPadSizeRequestPort,
                createStationLandingPadSizeRequestPort,
                deleteStationLandingPadSizeRequestPort,
                updateStationPort,
                sendKafkaMessagePort,
                retryTemplate,
                executor,
                objectMapper,
                messageMapper
        );
    }

    @Bean(name = "tradeStationPlanetaryInterModuleCommunicationService")
    public StationPlanetaryInterModuleCommunicationService stationPlanetaryInterModuleCommunicationService(
            @Qualifier("tradeIdGenerator") IdGenerator idGenerator,
            LoadStationsByFilterPort loadStationsByFilterPort,
            LoadAllStationPlanetaryRequestsPort loadAllStationPlanetaryRequestsPort,
            CreateOrLoadSystemPort createOrLoadSystemPort,
            CreateOrLoadStationPort createOrLoadStationPort,
            ExistsStationPlanetaryRequestPort existsStationPlanetaryRequestPort,
            CreateStationPlanetaryRequestPort createStationPlanetaryRequestPort,
            DeleteStationPlanetaryRequestPort deleteStationPlanetaryRequestPort,
            UpdateStationPort updateStationPort,
            SendKafkaMessagePort sendKafkaMessagePort,
            @Qualifier("tradeRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("tradeForkJoinPool") Executor executor,
            ObjectMapper objectMapper,
            MessageMapper messageMapper
    ) {
        return new StationPlanetaryInterModuleCommunicationService(
                idGenerator,
                loadStationsByFilterPort,
                loadAllStationPlanetaryRequestsPort,
                createOrLoadSystemPort,
                createOrLoadStationPort,
                existsStationPlanetaryRequestPort,
                createStationPlanetaryRequestPort,
                deleteStationPlanetaryRequestPort,
                updateStationPort,
                sendKafkaMessagePort,
                retryTemplate,
                executor,
                objectMapper,
                messageMapper
        );
    }

    @Bean(name = "tradeStationRequireOdysseyInterModuleCommunicationService")
    public StationRequireOdysseyInterModuleCommunicationService stationRequireOdysseyInterModuleCommunicationService(
            @Qualifier("tradeIdGenerator") IdGenerator idGenerator,
            LoadStationsByFilterPort loadStationsByFilterPort,
            LoadAllStationRequireOdysseyRequestsPort loadAllStationRequireOdysseyRequestsPort,
            CreateOrLoadSystemPort createOrLoadSystemPort,
            CreateOrLoadStationPort createOrLoadStationPort,
            ExistsStationRequireOdysseyRequestPort existsStationRequireOdysseyRequestPort,
            CreateStationRequireOdysseyRequestPort createStationRequireOdysseyRequestPort,
            DeleteStationRequireOdysseyRequestPort deleteStationRequireOdysseyRequestPort,
            UpdateStationPort updateStationPort,
            SendKafkaMessagePort sendKafkaMessagePort,
            @Qualifier("tradeRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("tradeForkJoinPool") Executor executor,
            ObjectMapper objectMapper,
            MessageMapper messageMapper
    ) {
        return new StationRequireOdysseyInterModuleCommunicationService(
                idGenerator,
                loadStationsByFilterPort,
                loadAllStationRequireOdysseyRequestsPort,
                createOrLoadSystemPort,
                createOrLoadStationPort,
                existsStationRequireOdysseyRequestPort,
                createStationRequireOdysseyRequestPort,
                deleteStationRequireOdysseyRequestPort,
                updateStationPort,
                sendKafkaMessagePort,
                retryTemplate,
                executor,
                objectMapper,
                messageMapper
        );
    }

    @Bean(name = "tradeSystemEliteIdInterModuleCommunicationService")
    public SystemEliteIdInterModuleCommunicationService systemEliteIdInterModuleCommunicationService(
            @Qualifier("tradeIdGenerator") IdGenerator idGenerator,
            LoadSystemsByFilterPort loadSystemsByFilterPort,
            LoadAllSystemEliteIdRequestsPort loadAllSystemEliteIdRequestsPort,
            CreateOrLoadSystemPort createOrLoadSystemPort,
            ExistsSystemEliteIdRequestPort existsSystemEliteIdRequestPort,
            CreateSystemEliteIdRequestPort createSystemEliteIdRequestPort,
            DeleteSystemEliteIdRequestPort deleteSystemEliteIdRequestPort,
            UpdateSystemPort updateSystemPort,
            SendKafkaMessagePort sendKafkaMessagePort,
            @Qualifier("tradeRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("tradeForkJoinPool") Executor executor,
            ObjectMapper objectMapper,
            MessageMapper messageMapper) {
        return new SystemEliteIdInterModuleCommunicationService(
                idGenerator,
                loadSystemsByFilterPort,
                loadAllSystemEliteIdRequestsPort,
                createOrLoadSystemPort,
                existsSystemEliteIdRequestPort,
                createSystemEliteIdRequestPort,
                deleteSystemEliteIdRequestPort,
                updateSystemPort,
                sendKafkaMessagePort,
                retryTemplate,
                executor,
                objectMapper,
                messageMapper
        );
    }
}
