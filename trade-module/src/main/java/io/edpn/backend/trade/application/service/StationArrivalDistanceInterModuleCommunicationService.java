package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationArrivalDistanceResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.domain.filter.FindStationFilter;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import io.edpn.backend.trade.application.port.incomming.kafka.RequestDataUseCase;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.station.CreateOrLoadStationPort;
import io.edpn.backend.trade.application.port.outgoing.station.LoadStationsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.station.UpdateStationPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.CleanUpObsoleteStationArrivalDistanceRequestsUseCase;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.CreateStationArrivalDistanceRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.DeleteStationArrivalDistanceRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.ExistsStationArrivalDistanceRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.LoadAllStationArrivalDistanceRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.RequestMissingStationArrivalDistanceUseCase;
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
public class StationArrivalDistanceInterModuleCommunicationService implements RequestDataUseCase<Station>, RequestMissingStationArrivalDistanceUseCase, ReceiveKafkaMessageUseCase<StationArrivalDistanceResponse>, CleanUpObsoleteStationArrivalDistanceRequestsUseCase {
    public static final FindStationFilter FIND_STATION_FILTER = FindStationFilter.builder()
            .hasArrivalDistance(false)
            .build();

    private final IdGenerator idGenerator;
    private final LoadStationsByFilterPort loadStationsByFilterPort;
    private final LoadAllStationArrivalDistanceRequestsPort loadAllStationArrivalDistanceRequestsPort;
    private final CreateOrLoadSystemPort createOrLoadSystemPort;
    private final CreateOrLoadStationPort createOrLoadStationPort;
    private final ExistsStationArrivalDistanceRequestPort existsStationArrivalDistanceRequestPort;
    private final CreateStationArrivalDistanceRequestPort createStationArrivalDistanceRequestPort;
    private final DeleteStationArrivalDistanceRequestPort deleteStationArrivalDistanceRequestPort;
    private final UpdateStationPort updateStationPort;
    private final SendKafkaMessagePort sendKafkaMessagePort;
    private final RetryTemplate retryTemplate;
    private final Executor executor;
    private final ObjectMapper objectMapper;

    @Override
    public void receive(StationArrivalDistanceResponse message) {
        String systemName = message.systemName();
        String stationName = message.stationName();
        double arrivalDistance = message.arrivalDistance();

        CompletableFuture.supplyAsync(() ->
                        createOrLoadSystemPort.createOrLoad(
                                new System(idGenerator.generateId(), null, systemName, null)))
                .thenCompose(loadedSystem -> CompletableFuture.supplyAsync(() ->
                        createOrLoadStationPort.createOrLoad(
                                new Station(
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
                        updateStationPort.update(station.withArrivalDistance(arrivalDistance));
                        deleteStationArrivalDistanceRequestPort.delete(systemName, stationName);
                    }
                })
                .join();
    }

    @Override
    @Scheduled(cron = "0 0 4 * * *")
    public synchronized void cleanUpObsolete() {
        // find all open request in database
        List<StationDataRequest> dataRequests = loadAllStationArrivalDistanceRequestsPort.loadAll();
        // find all items that have missing info
        List<Station> missingItemsList = loadStationsByFilterPort.loadByFilter(FIND_STATION_FILTER);
        // items that are in open requests, but not in items with missing info can be removed
        dataRequests.stream()
                .filter(dataRequest -> missingItemsList.stream()
                        .noneMatch(station -> station.name().equals(dataRequest.stationName()) && station.system().name().equals(dataRequest.systemName())))
                .forEach(dataRequest -> deleteStationArrivalDistanceRequestPort.delete(dataRequest.systemName(), dataRequest.stationName()));
        log.info("cleaned obsolete StationArrivalDistanceRequests");
    }

    @Override
    @Scheduled(cron = "0 0 0/12 * * *")
    public synchronized void requestMissing() {
        loadStationsByFilterPort.loadByFilter(FIND_STATION_FILTER).parallelStream()
                .forEach(station ->
                        CompletableFuture.runAsync(() -> {
                            StationDataRequest stationDataRequest = new StationDataRequest(Module.TRADE, station.name(), station.system().name());

                            JsonNode jsonNode = objectMapper.valueToTree(stationDataRequest);
                            Message message = new Message(Topic.Request.STATION_ARRIVAL_DISTANCE.getTopicName(), jsonNode.toString());

                            boolean sendSuccessful = retryTemplate.execute(retryContext -> sendKafkaMessagePort.send(message));
                            if (sendSuccessful) {
                                createStationArrivalDistanceRequestPort.create(station.system().name(), station.name());
                            }
                        }, executor));
        log.info("requested missing StationArrivalDistance");
    }

    @Override
    public boolean isApplicable(Station station) {
        return Objects.isNull(station.arrivalDistance());
    }

    @Override
    public synchronized void request(Station station) {
        String stationName = station.name();
        String systemName = station.system().name();
        boolean shouldRequest = !existsStationArrivalDistanceRequestPort.exists(systemName, stationName);
        if (shouldRequest) {
            StationDataRequest stationDataRequest = new StationDataRequest(
                    Module.TRADE, station.name(), station.system().name()
            );

            JsonNode jsonNode = objectMapper.valueToTree(stationDataRequest);
            Message message = new Message(Topic.Request.STATION_ARRIVAL_DISTANCE.getTopicName(), jsonNode.toString());

            sendKafkaMessagePort.send(message);
            createStationArrivalDistanceRequestPort.create(systemName, stationName);
        }
    }
}
