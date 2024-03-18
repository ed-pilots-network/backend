package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.domain.filter.FindSystemFilter;
import io.edpn.backend.trade.application.domain.intermodulecommunication.SystemDataRequest;
import io.edpn.backend.trade.application.domain.intermodulecommunication.SystemEliteIdResponse;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import io.edpn.backend.trade.application.port.incomming.kafka.RequestDataUseCase;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.system.CreateOrLoadSystemPort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.system.UpdateSystemPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.CleanUpObsoleteSystemEliteIdRequestsUseCase;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.CreateIfNotExistsSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.ExistsSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.LoadAllSystemEliteIdRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.RequestMissingSystemEliteIdUseCase;
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
public class SystemEliteIdInterModuleCommunicationService implements RequestDataUseCase<System>, RequestMissingSystemEliteIdUseCase, ReceiveKafkaMessageUseCase<SystemEliteIdResponse>, CleanUpObsoleteSystemEliteIdRequestsUseCase {
    public static final FindSystemFilter FIND_SYSTEM_FILTER = FindSystemFilter.builder()
            .hasEliteId(false)
            .build();
    
    private final IdGenerator idGenerator;
    private final LoadSystemsByFilterPort loadSystemsByFilterPort;
    private final LoadAllSystemEliteIdRequestsPort loadAllSystemEliteIdRequestsPort;
    private final CreateOrLoadSystemPort createOrLoadSystemPort;
    private final ExistsSystemEliteIdRequestPort existsSystemEliteIdRequestPort;
    private final CreateIfNotExistsSystemEliteIdRequestPort createIfNotExistsSystemEliteIdRequestPort;
    private final DeleteSystemEliteIdRequestPort deleteSystemEliteIdRequestPort;
    private final UpdateSystemPort updateSystemPort;
    private final SendKafkaMessagePort sendKafkaMessagePort;
    private final RetryTemplate retryTemplate;
    private final Executor executor;
    private final ObjectMapper objectMapper;
    
    @Override
    public boolean isApplicable(System system) {
        return Objects.isNull(system.eliteId());
    }
    
    @Override
    public synchronized void request(System system) {
        String systemName = system.name();
        SystemDataRequest systemDataRequest = new SystemDataRequest(
                Module.TRADE, systemName
        );
        JsonNode jsonNode = objectMapper.valueToTree(systemDataRequest);
        Message message = new Message(Topic.Request.SYSTEM_ELITE_ID.getTopicName(), jsonNode.toString());
        
        sendKafkaMessagePort.send(message);
        createIfNotExistsSystemEliteIdRequestPort.createIfNotExists(systemName);
    }
    
    @Override
    @Scheduled(cron = "0 0 0/12 * * *")
    public void requestMissing() {
        loadSystemsByFilterPort.loadByFilter(FIND_SYSTEM_FILTER).parallelStream()
                .forEach(system ->
                        CompletableFuture.runAsync(() -> {
                            SystemDataRequest systemDataRequest = new SystemDataRequest(Module.TRADE, system.name());
                            
                            JsonNode jsonNode = objectMapper.valueToTree(systemDataRequest);
                            Message message = new Message(Topic.Request.SYSTEM_ELITE_ID.getTopicName(), jsonNode.toString());
                            
                            boolean sendSuccessful = retryTemplate.execute(retryContext -> sendKafkaMessagePort.send(message));
                            if (sendSuccessful) {
                                createIfNotExistsSystemEliteIdRequestPort.createIfNotExists(system.name());
                            }
                        }, executor));
        log.info("requested missing SystemEliteId");
    }
    
    @Override
    @Scheduled(cron = "0 0 4 * * *")
    public synchronized void cleanUpObsolete() {
        // find all open request in database
        List<SystemDataRequest> dataRequests = loadAllSystemEliteIdRequestsPort.loadAll();
        // find all items that have missing info
        List<System> missingItemsList = loadSystemsByFilterPort.loadByFilter(FIND_SYSTEM_FILTER);
        // items that are in open requests, but not in items with missing info can be removed
        dataRequests.stream()
                .filter(dataRequest -> missingItemsList.stream()
                        .noneMatch(system -> system.name().equals(dataRequest.systemName())))
                .forEach(dataRequest -> deleteSystemEliteIdRequestPort.delete(dataRequest.systemName()));
        log.info("cleaned obsolete SystemEliteIdRequests");
    }
    
    @Override
    public void receive(SystemEliteIdResponse message) {
        String systemName = message.systemName();
        long eliteId = message.eliteId();
        
        CompletableFuture.supplyAsync(() ->
                        createOrLoadSystemPort.createOrLoad(
                                new System(idGenerator.generateId(), null, systemName, null)))
                .whenComplete((station, throwable) -> {
                    if (throwable != null) {
                        log.error("Exception occurred in retrieving system", throwable);
                    } else {
                        updateSystemPort.update(station.withEliteId(eliteId));
                        deleteSystemEliteIdRequestPort.delete(systemName);
                    }
                })
                .join();
    }
}
