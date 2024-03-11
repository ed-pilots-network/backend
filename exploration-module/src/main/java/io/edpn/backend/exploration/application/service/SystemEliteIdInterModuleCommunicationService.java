package io.edpn.backend.exploration.application.service;

import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;
import io.edpn.backend.exploration.application.port.incomming.ProcessPendingDataRequestUseCase;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.CreateIfNotExistsSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.LoadAllSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.SystemEliteIdResponseSender;
import io.edpn.backend.exploration.application.domain.intermodulecommunication.SystemDataRequest;
import io.edpn.backend.util.ConcurrencyUtil;
import io.edpn.backend.util.Module;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
@Slf4j
public class SystemEliteIdInterModuleCommunicationService implements ReceiveKafkaMessageUseCase<SystemDataRequest>, ProcessPendingDataRequestUseCase<SystemEliteIdRequest> {


    private final LoadAllSystemEliteIdRequestPort loadAllSystemEliteIdRequestPort;
    private final CreateIfNotExistsSystemEliteIdRequestPort createIfNotExistsSystemEliteIdRequestPort;
    private final LoadSystemPort loadSystemPort;
    private final SystemEliteIdResponseSender systemEliteIdResponseSender;
    private final ExecutorService executorService;


    @Override
    public void receive(SystemDataRequest message) {
        String systemName = message.systemName();
        Module requestingModule = message.requestingModule();

        SystemEliteIdRequest systemEliteIdRequest = new SystemEliteIdRequest(systemName, requestingModule);
        createIfNotExistsSystemEliteIdRequestPort.createIfNotExists(systemEliteIdRequest);

        executorService.submit(ConcurrencyUtil.errorHandlingWrapper(
                sendEventIfDataExists(systemEliteIdRequest),
                exception -> log.error("Error while sending systemEliteIdResponse for system: {}", systemName, exception)));
    }

    @Override
    @Scheduled(cron = "0 0 0/12 * * *")
    public void processPending() {
        loadAllSystemEliteIdRequestPort.loadAll()
                .parallelStream()
                .map(this::sendEventIfDataExists)
                .map(runnable -> ConcurrencyUtil.errorHandlingWrapper(
                        runnable,
                        exception -> log.error("Error while sending systemEliteIdResponse while processing all pending requests", exception)))
                .forEach(executorService::submit);
    }

    private Runnable sendEventIfDataExists(SystemEliteIdRequest request) {
        return () -> loadSystemPort.load(request.systemName())
                .ifPresent(system -> systemEliteIdResponseSender.sendResponsesForSystem(system.name()));
    }
}
