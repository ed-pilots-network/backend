package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.trade.application.domain.Coordinate;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.domain.filter.FindSystemFilter;
import io.edpn.backend.trade.application.domain.intermodulecommunication.SystemCoordinatesResponse;
import io.edpn.backend.trade.application.domain.intermodulecommunication.SystemDataRequest;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import io.edpn.backend.trade.application.port.incomming.kafka.RequestDataUseCase;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.system.CreateOrLoadSystemPort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.system.UpdateSystemPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.CleanUpObsoleteSystemCoordinateRequestsUseCase;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.CreateIfNotExistsSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.ExistsSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.LoadAllSystemCoordinateRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.RequestMissingSystemCoordinatesUseCase;
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
public class SystemCoordinateInterModuleCommunicationService implements RequestDataUseCase<System>, RequestMissingSystemCoordinatesUseCase, ReceiveKafkaMessageUseCase<SystemCoordinatesResponse>, CleanUpObsoleteSystemCoordinateRequestsUseCase {
    public static final FindSystemFilter FIND_SYSTEM_FILTER = FindSystemFilter.builder()
            .hasCoordinates(false)
            .build();
    
    private final IdGenerator idGenerator;
    private final LoadSystemsByFilterPort loadSystemsByFilterPort;
    private final LoadAllSystemCoordinateRequestsPort loadAllSystemCoordinateRequestsPort;
    private final CreateOrLoadSystemPort createOrLoadSystemPort;
    private final ExistsSystemCoordinateRequestPort existsSystemCoordinateRequestPort;
    private final CreateIfNotExistsSystemCoordinateRequestPort createIfNotExistsSystemCoordinateRequestPort;
    private final DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort;
    private final UpdateSystemPort updateSystemPort;
    private final SendKafkaMessagePort sendKafkaMessagePort;
    private final RetryTemplate retryTemplate;
    private final Executor executor;
    private final ObjectMapper objectMapper;
    
    @Override
    public boolean isApplicable(System system) {
        return Objects.isNull(system.coordinate().x()) || Objects.isNull(system.coordinate().y()) || Objects.isNull(system.coordinate().z());
    }
    
    @Override
    public synchronized void request(System system) {
        final String systemName = system.name();
        SystemDataRequest systemDataRequest = new SystemDataRequest(Module.TRADE, systemName);
        
        JsonNode jsonNode = objectMapper.valueToTree(systemDataRequest);
        Message message = new Message(Topic.Request.SYSTEM_COORDINATES.getTopicName(), jsonNode.toString());
        
        sendKafkaMessagePort.send(message);
        createIfNotExistsSystemCoordinateRequestPort.createIfNotExists(systemName);
        
    }
    
    @Override
    @Scheduled(cron = "0 0 0/12 * * *")
    public void requestMissing() {
        FindSystemFilter filter = FindSystemFilter.builder()
                .hasCoordinates(false)
                .hasEliteId(null)
                .name(null)
                .build();
        
        loadSystemsByFilterPort.loadByFilter(filter).parallelStream()
                .forEach(system ->
                        CompletableFuture.runAsync(() -> {
                            SystemDataRequest systemDataRequest = new SystemDataRequest(Module.TRADE, system.name());
                            
                            JsonNode jsonNode = objectMapper.valueToTree(systemDataRequest);
                            Message message = new Message(Topic.Request.SYSTEM_COORDINATES.getTopicName(), jsonNode.toString());
                            
                            boolean sendSuccessful = retryTemplate.execute(retryContext -> sendKafkaMessagePort.send(message));
                            if (sendSuccessful) {
                                createIfNotExistsSystemCoordinateRequestPort.createIfNotExists(system.name());
                            }
                        }, executor));
        log.info("requested missing SystemCoordinate");
    }
    
    @Override
    @Scheduled(cron = "0 0 4 * * *")
    public synchronized void cleanUpObsolete() {
        // find all open request in database
        List<SystemDataRequest> dataRequests = loadAllSystemCoordinateRequestsPort.loadAll();
        // find all items that have missing info
        List<System> missingItemsList = loadSystemsByFilterPort.loadByFilter(FIND_SYSTEM_FILTER);
        // items that are in open requests, but not in items with missing info can be removed
        dataRequests.stream()
                .filter(dataRequest -> missingItemsList.stream()
                        .noneMatch(system -> system.name().equals(dataRequest.systemName())))
                .forEach(dataRequest -> deleteSystemCoordinateRequestPort.delete(dataRequest.systemName()));
        log.info("cleaned obsolete SystemCoordinateRequests");
    }
    
    @Override
    public void receive(SystemCoordinatesResponse message) {
        final String systemName = message.systemName();
        final double xCoordinate = message.xCoordinate();
        final double yCoordinate = message.yCoordinate();
        final double zCoordinate = message.zCoordinate();
        
        CompletableFuture.supplyAsync(() ->
                        createOrLoadSystemPort.createOrLoad(
                                new System(
                                        idGenerator.generateId(),
                                        null,
                                        systemName,
                                        null)))
                .whenComplete((system, throwable) -> {
                    if (throwable != null) {
                        log.error("Exception occurred in retrieving system", throwable);
                    } else {
                        updateSystemPort.update(system.withCoordinate(new Coordinate(xCoordinate, yCoordinate, zCoordinate)));
                        deleteSystemCoordinateRequestPort.delete(systemName);
                    }
                })
                .join();
    }
}
