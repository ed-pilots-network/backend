package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.domain.filter.FindSystemFilter;
import io.edpn.backend.trade.application.dto.web.object.mapper.MessageMapper;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import io.edpn.backend.trade.application.port.incomming.kafka.RequestDataUseCase;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadOrCreateSystemByNamePort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.system.UpdateSystemPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.CleanUpObsoleteSystemCoordinateRequestsUseCase;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.CreateSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.ExistsSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.LoadAllSystemCoordinateRequestsPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.RequestMissingSystemCoordinatesUseCase;
import io.edpn.backend.util.Module;
import io.edpn.backend.util.Topic;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
@Slf4j
public class SystemCoordinateInterModuleCommunicationService implements RequestDataUseCase<System>, RequestMissingSystemCoordinatesUseCase, ReceiveKafkaMessageUseCase<SystemCoordinatesResponse>, CleanUpObsoleteSystemCoordinateRequestsUseCase {
    public static final FindSystemFilter FIND_SYSTEM_FILTER = FindSystemFilter.builder()
            .hasCoordinates(false)
            .build();

    private final LoadSystemsByFilterPort loadSystemsByFilterPort;
    private final LoadAllSystemCoordinateRequestsPort loadAllSystemCoordinateRequestsPort;
    private final LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort;
    private final ExistsSystemCoordinateRequestPort existsSystemCoordinateRequestPort;
    private final CreateSystemCoordinateRequestPort createSystemCoordinateRequestPort;
    private final DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort;
    private final UpdateSystemPort updateSystemPort;
    private final SendKafkaMessagePort sendKafkaMessagePort;
    private final RetryTemplate retryTemplate;
    private final Executor executor;
    private final ObjectMapper objectMapper;
    private final MessageMapper messageMapper;

    @Override
    public boolean isApplicable(System system) {
        return Objects.isNull(system.getXCoordinate()) || Objects.isNull(system.getYCoordinate()) || Objects.isNull(system.getZCoordinate());
    }

    @Override
    public synchronized void request(System system) {
        String systemName = system.getName();
        boolean shouldRequest = !existsSystemCoordinateRequestPort.exists(systemName);
        if (shouldRequest) {
            SystemDataRequest systemDataRequest = new SystemDataRequest(
                    Module.TRADE, systemName
            );
            JsonNode jsonNode = objectMapper.valueToTree(systemDataRequest);

            Message message = Message.builder()
                    .topic(Topic.Request.SYSTEM_ELITE_ID.getTopicName())
                    .message(jsonNode.toString())
                    .build();

            sendKafkaMessagePort.send(messageMapper.map(message));
            createSystemCoordinateRequestPort.create(systemName);
        }
    }

    @Override
    @Scheduled(cron = "0 0 0/12 * * *")
    public void requestMissing() {
        loadSystemsByFilterPort.loadByFilter(FIND_SYSTEM_FILTER).parallelStream()
                .forEach(system ->
                        CompletableFuture.runAsync(() -> {
                            SystemDataRequest systemDataRequest = new SystemDataRequest(Module.TRADE, system.getName());

                            JsonNode jsonNode = objectMapper.valueToTree(systemDataRequest);

                            Message message = Message.builder()
                                    .topic("systemCoordinateRequest")
                                    .message(jsonNode.toString())
                                    .build();

                            boolean sendSuccessful = retryTemplate.execute(retryContext ->
                                    sendKafkaMessagePort.send(messageMapper.map(message)));
                            if (sendSuccessful) {
                                createSystemCoordinateRequestPort.create(system.getName());
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
                        .noneMatch(system -> system.getName().equals(dataRequest.systemName())))
                .forEach(dataRequest -> deleteSystemCoordinateRequestPort.delete(dataRequest.systemName()));
        log.info("cleaned obsolete SystemCoordinateRequests");
    }

    @Override
    public void receive(SystemCoordinatesResponse message) {
        final String systemName = message.systemName();
        final double xCoordinate = message.xCoordinate();
        final double yCoordinate = message.yCoordinate();
        final double zCoordinate = message.zCoordinate();

        CompletableFuture<System> systemCompletableFuture = CompletableFuture.supplyAsync(() -> loadOrCreateSystemByNamePort.loadOrCreateSystemByName(systemName))
                .whenComplete((system, throwable) -> {
                    if (throwable != null) {
                        log.error("Exception occurred in retrieving system", throwable);
                    } else {
                        system.setXCoordinate(xCoordinate);
                        system.setYCoordinate(yCoordinate);
                        system.setZCoordinate(zCoordinate);
                    }
                });

        updateSystemPort.update(systemCompletableFuture.join());
        deleteSystemCoordinateRequestPort.delete(systemName);
    }
}
