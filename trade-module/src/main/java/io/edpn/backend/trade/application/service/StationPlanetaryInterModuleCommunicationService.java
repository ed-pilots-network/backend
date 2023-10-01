package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationPlanetaryResponse;
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
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.CleanUpObsoleteStationPlanetaryRequestsUseCase;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.CreateStationPlanetaryRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.DeleteStationPlanetaryRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.ExistsStationPlanetaryRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.LoadAllStationPlanetaryRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.RequestMissingStationPlanetaryUseCase;
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
public class StationPlanetaryInterModuleCommunicationService implements RequestDataUseCase<Station>, RequestMissingStationPlanetaryUseCase, ReceiveKafkaMessageUseCase<StationPlanetaryResponse>, CleanUpObsoleteStationPlanetaryRequestsUseCase {

    public static final FindStationFilter FIND_STATION_FILTER = FindStationFilter.builder()
            .hasPlanetary(false)
            .build();

    private final IdGenerator idGenerator;
    private final LoadStationsByFilterPort loadStationsByFilterPort;
    private final LoadAllStationPlanetaryRequestsPort loadAllStationPlanetaryRequestsPort;
    private final CreateOrLoadSystemPort createOrLoadSystemPort;
    private final CreateOrLoadStationPort createOrLoadStationPort;
    private final ExistsStationPlanetaryRequestPort existsStationPlanetaryRequestPort;
    private final CreateStationPlanetaryRequestPort createStationPlanetaryRequestPort;
    private final DeleteStationPlanetaryRequestPort deleteStationPlanetaryRequestPort;
    private final UpdateStationPort updateStationPort;
    private final SendKafkaMessagePort sendKafkaMessagePort;
    private final RetryTemplate retryTemplate;
    private final Executor executor;
    private final ObjectMapper objectMapper;
    private final MessageMapper messageMapper;

    @Override
    public boolean isApplicable(Station station) {
        return Objects.isNull(station.getPlanetary());
    }

    @Override
    public synchronized void request(Station station) {
        String stationName = station.getName();
        String systemName = station.getSystem().getName();
        boolean shouldRequest = !existsStationPlanetaryRequestPort.exists(systemName, stationName);
        if (shouldRequest) {
            StationDataRequest stationDataRequest = new StationDataRequest(
                    Module.TRADE, stationName, systemName
            );

            JsonNode jsonNode = objectMapper.valueToTree(stationDataRequest);

            Message message = Message.builder()
                    .topic(Topic.Request.STATION_IS_PLANETARY.getTopicName())
                    .message(jsonNode.toString())
                    .build();

            sendKafkaMessagePort.send(messageMapper.map(message));
            createStationPlanetaryRequestPort.create(systemName, stationName);
        }
    }

    @Override
    @Scheduled(cron = "0 0 4 * * *")
    public synchronized void cleanUpObsolete() {
        // find all open request in database
        List<StationDataRequest> dataRequests = loadAllStationPlanetaryRequestsPort.loadAll();
        // find all items that have missing info
        List<Station> missingItemsList = loadStationsByFilterPort.loadByFilter(FIND_STATION_FILTER);
        // items that are in open requests, but not in items with missing info can be removed
        dataRequests.stream()
                .filter(dataRequest -> missingItemsList.stream()
                        .noneMatch(station -> station.getName().equals(dataRequest.stationName()) && station.getSystem().getName().equals(dataRequest.systemName())))
                .forEach(dataRequest -> deleteStationPlanetaryRequestPort.delete(dataRequest.systemName(), dataRequest.stationName()));
        log.info("cleaned obsolete StationPlanetaryRequests");
    }

    @Override
    public void receive(StationPlanetaryResponse message) {
        String systemName = message.systemName();
        String stationName = message.stationName();
        boolean planetary = message.planetary();

        //get system
        System system = System.builder().id(UUID.randomUUID()).name(systemName).build();
        CompletableFuture<System> systemCompletableFuture = CompletableFuture.supplyAsync(() -> createOrLoadSystemPort.createOrLoad(system));

        // get station
        CompletableFuture<Station> stationCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Station station = Station.builder().id(idGenerator.generateId()).system(systemCompletableFuture.copy().join()).name(stationName).build();
            return createOrLoadStationPort.createOrLoad(station);
        });
        stationCompletableFuture.whenComplete((station, throwable) -> {
            if (throwable != null) {
                log.error("Exception occurred in retrieving station", throwable);
            } else {
                station.setPlanetary(planetary);
            }
        });

        updateStationPort.update(stationCompletableFuture.join());
        deleteStationPlanetaryRequestPort.delete(systemName, stationName);
    }

    @Override
    @Scheduled(cron = "0 0 0/12 * * *")
    public void requestMissing() {
        loadStationsByFilterPort.loadByFilter(FIND_STATION_FILTER).parallelStream()
                .forEach(station ->
                        CompletableFuture.runAsync(() -> {
                            StationDataRequest stationDataRequest = new StationDataRequest(Module.TRADE, station.getName(), station.getSystem().getName());

                            JsonNode jsonNode = objectMapper.valueToTree(stationDataRequest);

                            Message message = Message.builder()
                                    .topic(Topic.Request.STATION_IS_PLANETARY.getTopicName())
                                    .message(jsonNode.toString())
                                    .build();

                            boolean sendSuccessful = retryTemplate.execute(retryContext ->
                                    sendKafkaMessagePort.send(messageMapper.map(message)));
                            if (sendSuccessful) {
                                createStationPlanetaryRequestPort.create(station.getSystem().getName(), station.getName());
                            }
                        }, executor));
        log.info("requested missing StationPlanetary");
    }
}
