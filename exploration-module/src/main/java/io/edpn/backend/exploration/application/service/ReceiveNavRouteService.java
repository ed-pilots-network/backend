package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.Coordinate;
import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.MessageDto;
import io.edpn.backend.exploration.application.dto.mapper.MessageMapper;
import io.edpn.backend.exploration.application.dto.mapper.SystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.CreateSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemCoordinateRequestBySystemNamePort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.SaveSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.SendKafkaMessagePort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.NavRouteMessage;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@RequiredArgsConstructor
@Slf4j
public class ReceiveNavRouteService implements ReceiveKafkaMessageUseCase<NavRouteMessage.V1> {

    private final static String TOPIC = "_systemCoordinatesDataResponse";


    private final CreateSystemPort createSystemPort;
    private final LoadSystemPort loadSystemPort;
    private final SaveSystemPort saveSystemPort;
    private final SendKafkaMessagePort sendKafkaMessagePort;
    private final LoadSystemCoordinateRequestBySystemNamePort loadSystemCoordinateRequestBySystemNamePort;
    private final DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort;
    private final SystemCoordinatesResponseMapper systemCoordinatesResponseMapper;
    private final MessageMapper messageMapper;
    private final ObjectMapper objectMapper;
    private final RetryTemplate retryTemplate;
    private final Executor executor;

    @Override
    public void receive(NavRouteMessage.V1 message) {
        long start = java.lang.System.nanoTime();
        log.debug("DefaultReceiveNavRouteMessageUseCase.receive -> CommodityMessage: {}", message);

        //LocalDateTime updateTimestamp = message.getMessageTimeStamp();
        NavRouteMessage.V1.Payload payload = message.message();
        NavRouteMessage.V1.Item[] routeItems = payload.items();


        CompletableFuture.allOf(List.of(routeItems).parallelStream()
                        .map(this::process)
                        .toArray(CompletableFuture[]::new))
                .join();

        log.trace("DefaultReceiveNavRouteMessageUseCase.receive -> took {} nanosecond", java.lang.System.nanoTime() - start);
        log.info("DefaultReceiveNavRouteMessageUseCase.receive -> the message has been processed");
    }

    private CompletableFuture<Void> process(NavRouteMessage.V1.Item item) {
        return loadOrCreateSystem(item.starSystem())
                .thenApply(system -> updateSystemFromItem(system, item))
                .thenComposeAsync(this::saveSystem)
                .thenAcceptAsync(this::sendResponse);
    }

    private CompletableFuture<System> loadOrCreateSystem(String systemName) {
        return CompletableFuture.supplyAsync(() -> loadSystemPort.load(systemName)
                .orElseGet(() -> createSystemPort.create(systemName)), executor);
    }

    private System updateSystemFromItem(final System system, NavRouteMessage.V1.Item item) {
        System returnSystem = system;
        if (Objects.isNull(system.eliteId())) {
            returnSystem = returnSystem.withEliteId(item.systemAddress());
        }

        if (Objects.isNull(system.starClass())) {
            returnSystem = returnSystem.withStarClass(item.starClass());
        }

        if (Objects.isNull(system.coordinate()) || Objects.isNull(system.coordinate().x()) || Objects.isNull(system.coordinate().y()) || Objects.isNull(system.coordinate().z())) {
            returnSystem = returnSystem.withCoordinate(new Coordinate(item.starPos()[0], item.starPos()[1], item.starPos()[2]));
        }
        return returnSystem;
    }

    private CompletableFuture<System> saveSystem(System system) {
        return CompletableFuture.supplyAsync(() -> saveSystemPort.save(system), executor);
    }

    private void sendResponse(System system) {
        loadSystemCoordinateRequestBySystemNamePort.loadByName(system.name()).parallelStream()
                .forEach(systemCoordinateRequest -> CompletableFuture.runAsync(() -> {
                    SystemCoordinatesResponse systemCoordinatesResponse = systemCoordinatesResponseMapper.map(system);
                    String stringJson = objectMapper.valueToTree(systemCoordinatesResponse).toString();
                    Message message = new Message(systemCoordinateRequest.requestingModule() + TOPIC, stringJson);
                    MessageDto messageDto = messageMapper.map(message);

                    boolean sendSuccessful = retryTemplate.execute(retryContext -> sendKafkaMessagePort.send(messageDto));
                    if (sendSuccessful) {
                        deleteSystemCoordinateRequestPort.delete(system.name(), systemCoordinateRequest.requestingModule());
                    }
                }, executor));

    }
}
