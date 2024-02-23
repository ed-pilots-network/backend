package io.edpn.backend.exploration.application.service;

import io.edpn.backend.exploration.application.domain.StationArrivalDistanceRequest;
import io.edpn.backend.exploration.application.port.incomming.ProcessPendingDataRequestUseCase;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.station.LoadStationPort;
import io.edpn.backend.exploration.application.port.outgoing.stationarrivaldistancerequest.CreateIfNotExistsStationArrivalDistanceRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.stationarrivaldistancerequest.LoadAllStationArrivalDistanceRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.stationarrivaldistancerequest.StationArrivalDistanceResponseSender;
import io.edpn.backend.util.ConcurrencyUtil;
import io.edpn.backend.util.Module;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
@Slf4j
public class StationArrivalDistanceInterModuleCommunicationService implements ReceiveKafkaMessageUseCase<StationArrivalDistanceRequest>, ProcessPendingDataRequestUseCase<StationArrivalDistanceRequest> {
    private final LoadAllStationArrivalDistanceRequestPort loadAllStationArrivalDistanceRequestPort;
    private final CreateIfNotExistsStationArrivalDistanceRequestPort createIfNotExistsStationArrivalDistanceRequestPort;
    private final LoadStationPort loadStationPort;
    private final StationArrivalDistanceResponseSender stationArrivalDistanceResponseSender;
    private final ExecutorService executorService;
    
    @Override
    public void receive(StationArrivalDistanceRequest message) {
        String systemName = message.systemName();
        String stationName = message.stationName();
        Module requestingModule = message.requestingModule();
        
        StationArrivalDistanceRequest stationArrivalDistanceRequest = new StationArrivalDistanceRequest(systemName, stationName, requestingModule);
        createIfNotExistsStationArrivalDistanceRequestPort.createIfNotExists(stationArrivalDistanceRequest);
        
        executorService.submit(ConcurrencyUtil.errorHandlingWrapper(
                sendEventIfDataExists(stationArrivalDistanceRequest),
                exception -> log.error("Error while sending stationArrivalDistanceResponse for station {} in system: {}", stationName, systemName, exception)));
    }
    
    @Override
    public void processPending() {
        loadAllStationArrivalDistanceRequestPort.loadAll()
                .parallelStream()
                .map(this::sendEventIfDataExists)
                .map(runnable -> ConcurrencyUtil.errorHandlingWrapper(
                        runnable,
                        exception -> log.error("Error while sending stationArrivalDistanceResponse while processing all pending requests", exception)))
                .forEach(executorService::submit);
    }
    
    private Runnable sendEventIfDataExists(StationArrivalDistanceRequest request) {
        return () -> loadStationPort.load(request.systemName(), request.stationName())
                .filter(station -> Objects.nonNull(station.distanceFromStar()))
                .ifPresent(station -> stationArrivalDistanceResponseSender.sendResponsesForStation(station.system().name(), station.name()));
    }
}
