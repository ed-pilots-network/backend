package io.edpn.backend.exploration.application.service;

import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.port.incomming.ProcessPendingDataRequestUseCase;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.CreateIfNotExistsSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadAllSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.SystemCoordinatesResponseSender;
import io.edpn.backend.exploration.application.domain.intermodulecommunication.SystemDataRequest;
import io.edpn.backend.util.ConcurrencyUtil;
import io.edpn.backend.util.Module;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
@Slf4j
public class SystemCoordinateInterModuleCommunicationService implements ReceiveKafkaMessageUseCase<SystemDataRequest>, ProcessPendingDataRequestUseCase<SystemCoordinateRequest> {


    private final LoadAllSystemCoordinateRequestPort loadAllSystemCoordinateRequestPort;
    private final CreateIfNotExistsSystemCoordinateRequestPort createIfNotExistsSystemCoordinateRequestPort;
    private final LoadSystemPort loadSystemPort;
    private final SystemCoordinatesResponseSender systemCoordinatesResponseSender;
    private final ExecutorService executorService;


    @Override
    public void receive(SystemDataRequest message) {
        String systemName = message.systemName();
        Module requestingModule = message.requestingModule();

        SystemCoordinateRequest systemCoordinateDataRequest = new SystemCoordinateRequest(systemName, requestingModule);
        createIfNotExistsSystemCoordinateRequestPort.createIfNotExists(systemCoordinateDataRequest);
        executorService.submit(ConcurrencyUtil.errorHandlingWrapper(
                sendEventIfDataExists(systemCoordinateDataRequest),
                exception -> log.error("Error while sending systemCoordinatesResponse for system: {}", systemName, exception)));
    }

    @Override
    @Scheduled(cron = "0 0 0/12 * * *")
    public void processPending() {
        loadAllSystemCoordinateRequestPort.loadAll()
                .parallelStream()
                .map(this::sendEventIfDataExists)
                .map(runnable -> ConcurrencyUtil.errorHandlingWrapper(
                        runnable,
                        exception -> log.error("Error while sending systemCoordinatesResponse while processing all pending requests", exception)))
                .forEach(executorService::submit);
    }

    private Runnable sendEventIfDataExists(SystemCoordinateRequest request) {
        return () -> loadSystemPort.load(request.systemName())
                .ifPresent(system -> systemCoordinatesResponseSender.sendResponsesForSystem(system.name()));
    }
}
