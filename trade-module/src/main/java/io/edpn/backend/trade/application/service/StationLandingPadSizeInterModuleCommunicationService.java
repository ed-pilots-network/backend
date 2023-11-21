package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationMaxLandingPadSizeResponse;
import io.edpn.backend.trade.application.domain.LandingPadSize;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.domain.filter.FindStationFilter;
import io.edpn.backend.trade.application.dto.web.object.mapper.MessageMapper;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import io.edpn.backend.trade.application.port.incomming.kafka.RequestDataUseCase;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.station.CreateOrLoadStationPort;
import io.edpn.backend.trade.application.port.outgoing.station.LoadStationsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.station.UpdateStationPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.CleanUpObsoleteStationLandingPadSizeRequestsUseCase;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.CreateStationLandingPadSizeRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.DeleteStationLandingPadSizeRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.ExistsStationLandingPadSizeRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.LoadAllStationLandingPadSizeRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.RequestMissingStationLandingPadSizeUseCase;
import io.edpn.backend.trade.application.port.outgoing.system.CreateOrLoadSystemPort;
import io.edpn.backend.util.IdGenerator;
import io.edpn.backend.util.Module;
import io.edpn.backend.util.Topic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@RequiredArgsConstructor
@Slf4j
public class StationLandingPadSizeInterModuleCommunicationService implements RequestDataUseCase<Station>, RequestMissingStationLandingPadSizeUseCase, ReceiveKafkaMessageUseCase<StationMaxLandingPadSizeResponse>, CleanUpObsoleteStationLandingPadSizeRequestsUseCase {

    public static final FindStationFilter FIND_STATION_FILTER = FindStationFilter.builder()
            .hasLandingPadSize(false)
            .build();

    private final IdGenerator idGenerator;
    private final LoadStationsByFilterPort loadStationsByFilterPort;
    private final LoadAllStationLandingPadSizeRequestsPort loadAllStationLandingPadSizeRequestsPort;
    private final CreateOrLoadSystemPort createOrLoadSystemPort;
    private final CreateOrLoadStationPort createOrLoadStationPort;
    private final ExistsStationLandingPadSizeRequestPort existsStationLandingPadSizeRequestPort;
    private final CreateStationLandingPadSizeRequestPort createStationLandingPadSizeRequestPort;
    private final DeleteStationLandingPadSizeRequestPort deleteStationLandingPadSizeRequestPort;
    private final UpdateStationPort updateStationPort;
    private final SendKafkaMessagePort sendKafkaMessagePort;
    private final RetryTemplate retryTemplate;
    private final Executor executor;
    private final ObjectMapper objectMapper;
    private final MessageMapper messageMapper;

    @Override
    public boolean isApplicable(Station station) {
        return Objects.isNull(station.maxLandingPadSize()) || LandingPadSize.UNKNOWN == station.maxLandingPadSize();
    }

    @Override
    public synchronized void request(Station station) {
        String stationName = station.name();
        String systemName = station.system().name();
        boolean shouldRequest = !existsStationLandingPadSizeRequestPort.exists(systemName, stationName);
        if (shouldRequest) {
            StationDataRequest stationDataRequest = new StationDataRequest(
                    Module.TRADE, stationName, systemName
            );
            JsonNode jsonNode = objectMapper.valueToTree(stationDataRequest);

            Message message = new Message(Topic.Request.STATION_MAX_LANDING_PAD_SIZE.getTopicName(), jsonNode.toString());

            sendKafkaMessagePort.send(messageMapper.map(message));
            createStationLandingPadSizeRequestPort.create(systemName, stationName);
        }
    }

    @Override
    @Scheduled(cron = "0 0 4 * * *")
    public synchronized void cleanUpObsolete() {
        // find all open request in database
        List<StationDataRequest> dataRequests = loadAllStationLandingPadSizeRequestsPort.loadAll();
        // find all items that have missing info
        List<Station> missingItemsList = loadStationsByFilterPort.loadByFilter(FIND_STATION_FILTER);
        // items that are in open requests, but not in items with missing info can be removed
        dataRequests.stream()
                .filter(dataRequest -> missingItemsList.stream()
                        .noneMatch(station -> station.name().equals(dataRequest.stationName()) && station.system().name().equals(dataRequest.systemName())))
                .forEach(dataRequest -> deleteStationLandingPadSizeRequestPort.delete(dataRequest.systemName(), dataRequest.stationName()));
        log.info("cleaned obsolete StationLandingPadSizeRequests");
    }

    @Override
    public void receive(StationMaxLandingPadSizeResponse message) {
        String systemName = message.systemName();
        String stationName = message.stationName();
        LandingPadSize landingPadSize = LandingPadSize.valueOf(message.maxLandingPadSize());


        CompletableFuture.supplyAsync(() ->
                        createOrLoadSystemPort.createOrLoad(new System(
                                idGenerator.generateId(),
                                null,
                                systemName,
                                null
                        )))
                .thenCompose(loadedSystem -> CompletableFuture.supplyAsync(() -> createOrLoadStationPort.createOrLoad(new Station(
                        idGenerator.generateId(),
                        null,
                        stationName,
                        null,
                        loadedSystem,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                ))))
                .whenComplete((station, throwable) -> {
                    if (throwable != null) {
                        log.error("Exception occurred in retrieving station", throwable);
                    } else {
                        updateStationPort.update(station.withMaxLandingPadSize(landingPadSize));
                        deleteStationLandingPadSizeRequestPort.delete(systemName, stationName);
                    }
                })
                .join();
    }

    @Override
    @Scheduled(cron = "0 0 0/12 * * *")
    public void requestMissing() {
        loadStationsByFilterPort.loadByFilter(FIND_STATION_FILTER).parallelStream()
                .forEach(station ->
                        CompletableFuture.runAsync(() -> {
                            StationDataRequest stationDataRequest = new StationDataRequest(Module.TRADE, station.name(), station.system().name());
                            boolean sendSuccessful = retryTemplate.execute(
                                    retryContext -> sendKafkaMessagePort.send(messageMapper.map(new Message(Topic.Request.STATION_MAX_LANDING_PAD_SIZE.getTopicName(), objectMapper.valueToTree(stationDataRequest).toString()))));
                            if (sendSuccessful) {
                                createStationLandingPadSizeRequestPort.create(station.system().name(), station.name());
                            }
                        }, executor));
        log.info("requested missing StationLandingPadSize");
    }
}
