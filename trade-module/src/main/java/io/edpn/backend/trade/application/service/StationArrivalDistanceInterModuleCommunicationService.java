package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationArrivalDistanceResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
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
import java.util.UUID;
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
    private final MessageMapper messageMapper;


    @Override
    public void receive(StationArrivalDistanceResponse message) {
        String systemName = message.systemName();
        String stationName = message.stationName();
        double arrivalDistance = message.arrivalDistance();

        //get system
        System system = System.builder().id(idGenerator.generateId()).name(systemName).build();
        CompletableFuture<System> systemCompletableFuture = CompletableFuture.supplyAsync(() -> createOrLoadSystemPort.createOrLoad(system));

        // get station
        CompletableFuture<Station> stationCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Station station = Station.builder().id(idGenerator.generateId()).name(stationName)
                    .system(systemCompletableFuture.join())
                    .build();
            return createOrLoadStationPort.createOrLoad(station);
        });
        stationCompletableFuture.whenComplete((station, throwable) -> {
            if (throwable != null) {
                log.error("Exception occurred in retrieving station", throwable);
            } else {
                station.setArrivalDistance(arrivalDistance);
            }
        });

        updateStationPort.update(stationCompletableFuture.join());
        deleteStationArrivalDistanceRequestPort.delete(systemName, stationName);
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
                        .noneMatch(station -> station.getName().equals(dataRequest.stationName()) && station.getSystem().getName().equals(dataRequest.systemName())))
                .forEach(dataRequest -> deleteStationArrivalDistanceRequestPort.delete(dataRequest.systemName(), dataRequest.stationName()));
        log.info("cleaned obsolete StationArrivalDistanceRequests");
    }

    @Override
    @Scheduled(cron = "0 0 0/12 * * *")
    public synchronized void requestMissing() {
        loadStationsByFilterPort.loadByFilter(FIND_STATION_FILTER).parallelStream()
                .forEach(station ->
                        CompletableFuture.runAsync(() -> {
                            StationDataRequest stationDataRequest = new StationDataRequest(Module.TRADE, station.getName(), station.getSystem().getName());

                            JsonNode jsonNode = objectMapper.valueToTree(stationDataRequest);

                            Message message = Message.builder()
                                    .topic(Topic.Request.STATION_ARRIVAL_DISTANCE.getTopicName())
                                    .message(jsonNode.toString())
                                    .build();

                            boolean sendSuccessful = retryTemplate.execute(retryContext ->
                                    sendKafkaMessagePort.send(messageMapper.map(message)));
                            if (sendSuccessful) {
                                createStationArrivalDistanceRequestPort.create(station.getSystem().getName(), station.getName());
                            }
                        }, executor));
        log.info("requested missing StationArrivalDistance");
    }

    @Override
    public boolean isApplicable(Station station) {
        return Objects.isNull(station.getArrivalDistance());
    }

    @Override
    public synchronized void request(Station station) {
        String stationName = station.getName();
        String systemName = station.getSystem().getName();
        boolean shouldRequest = !existsStationArrivalDistanceRequestPort.exists(systemName, stationName);
        if (shouldRequest) {
            StationDataRequest stationDataRequest = new StationDataRequest(
                    Module.TRADE, station.getName(), station.getSystem().getName()
            );

            JsonNode jsonNode = objectMapper.valueToTree(stationDataRequest);

            Message message = Message.builder()
                    .topic(Topic.Request.STATION_ARRIVAL_DISTANCE.getTopicName())
                    .message(jsonNode.toString())
                    .build();

            sendKafkaMessagePort.send(messageMapper.map(message));
            createStationArrivalDistanceRequestPort.create(systemName, stationName);
        }
    }
}
