package io.edpn.backend.exploration.application.service;

import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;
import io.edpn.backend.exploration.application.port.incomming.ProcessPendingDataRequestUseCase;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.CreateIfNotExistsSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.LoadAllSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.SystemEliteIdResponseSender;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.util.Module;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.ExecutorService;

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

        saveRequest(systemName, requestingModule);
        executorService.submit(sendEventIfDataExists(systemName));
    }

    @Override
    @Scheduled(cron = "0 0 0/12 * * *")
    public void processPending() {
        loadAllSystemEliteIdRequestPort.loadAll()
                .parallelStream()
                .map(SystemEliteIdRequest::systemName)
                .map(this::sendEventIfDataExists)
                .forEach(executorService::submit);
    }

    private void saveRequest(String systemName, Module requestingModule) {
        SystemEliteIdRequest systemEliteIdRequest = new SystemEliteIdRequest(systemName, requestingModule);
        createIfNotExistsSystemEliteIdRequestPort.createIfNotExists(systemEliteIdRequest);
    }

    private Runnable sendEventIfDataExists(String systemName) {
        return () -> loadSystemPort.load(systemName)
                .ifPresent(system -> systemEliteIdResponseSender.sendResponsesForSystem(system.name()));
    }
}
