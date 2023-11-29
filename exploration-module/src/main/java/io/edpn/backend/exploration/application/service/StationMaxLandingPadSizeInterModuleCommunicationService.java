package io.edpn.backend.exploration.application.service;

import io.edpn.backend.exploration.application.domain.StationMaxLandingPadSizeRequest;
import io.edpn.backend.exploration.application.port.incomming.ProcessPendingDataRequestUseCase;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.station.LoadStationPort;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.CreateIfNotExistsStationMaxLandingPadSizeRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.LoadAllStationMaxLandingPadSizeRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.StationMaxLandingPadSizeResponseSender;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.util.ConcurrencyUtil;
import io.edpn.backend.util.Module;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
@Slf4j
public class StationMaxLandingPadSizeInterModuleCommunicationService implements ReceiveKafkaMessageUseCase<StationDataRequest>, ProcessPendingDataRequestUseCase<StationMaxLandingPadSizeRequest> {


    private final LoadAllStationMaxLandingPadSizeRequestPort loadAllStationMaxLandingPadSizeRequestPort;
    private final CreateIfNotExistsStationMaxLandingPadSizeRequestPort createIfNotExistsStationMaxLandingPadSizeRequestPort;
    private final LoadStationPort loadStationPort;
    private final StationMaxLandingPadSizeResponseSender stationMaxLandingPadSizeResponseSender;
    private final ExecutorService executorService;


    @Override
    public void receive(StationDataRequest message) {
        String systemName = message.systemName();
        String stationName = message.stationName();
        Module requestingModule = message.requestingModule();


        StationMaxLandingPadSizeRequest stationMaxLandingPadSizeRequest = new StationMaxLandingPadSizeRequest(systemName, stationName, requestingModule);
        createIfNotExistsStationMaxLandingPadSizeRequestPort.createIfNotExists(stationMaxLandingPadSizeRequest);

        executorService.submit(ConcurrencyUtil.errorHandlingWrapper(
                sendEventIfDataExists(stationMaxLandingPadSizeRequest),
                exception -> log.error("Error while sending stationMaxLandingPadSizeResponse for station {} in system: {}", stationName, systemName, exception)));
    }

    @Override
    @Scheduled(cron = "0 0 0/12 * * *")
    public void processPending() {
        loadAllStationMaxLandingPadSizeRequestPort.loadAll()
                .parallelStream()
                .map(this::sendEventIfDataExists)
                .map(runnable -> ConcurrencyUtil.errorHandlingWrapper(
                        runnable,
                        exception -> log.error("Error while sending stationMaxLandingPadSizeResponse while processing all pending requests", exception)))
                .forEach(executorService::submit);
    }

    private Runnable sendEventIfDataExists(StationMaxLandingPadSizeRequest request) {
        return () -> loadStationPort.load(request.systemName(), request.stationName())
                .ifPresent(station -> stationMaxLandingPadSizeResponseSender.sendResponsesForStation(station.system().name(), station.name()));
    }
}
