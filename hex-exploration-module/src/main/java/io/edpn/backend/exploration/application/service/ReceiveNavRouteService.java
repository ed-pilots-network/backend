package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.adapter.kafka.dto.SystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.application.domain.Coordinate;
import io.edpn.backend.exploration.application.domain.KafkaMessage;
import io.edpn.backend.exploration.application.domain.System;
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

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

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
    private final ObjectMapper objectMapper;

    @Override
    public void receive(NavRouteMessage.V1 message) {
        long start = java.lang.System.nanoTime();
        log.debug("DefaultReceiveNavRouteMessageUseCase.receive -> CommodityMessage: {}", message);

        //LocalDateTime updateTimestamp = message.getMessageTimeStamp();
        NavRouteMessage.V1.Message payload = message.getMessage();
        NavRouteMessage.V1.Item[] routeItems = payload.getItems();


        CompletableFuture.allOf(List.of(routeItems).parallelStream()
                        .map(this::process)
                        .toArray(CompletableFuture[]::new))
                .join();

        log.trace("DefaultReceiveNavRouteMessageUseCase.receive -> took {} nanosecond", java.lang.System.nanoTime() - start);
        log.info("DefaultReceiveNavRouteMessageUseCase.receive -> the message has been processed");
    }

    private CompletableFuture<Void> process(NavRouteMessage.V1.Item item) {
        return loadOrCreateSystem(item.getStarSystem())
                .thenApply(system -> updateSystemFromItem(system, item))
                .thenComposeAsync(this::saveSystem)
                .thenAcceptAsync(this::sendResponse);
    }

    private CompletableFuture<System> loadOrCreateSystem(String systemName) {
        return CompletableFuture.supplyAsync(() -> loadSystemPort.load(systemName)
                .orElseGet(() -> createSystemPort.create(systemName)));
    }

    private System updateSystemFromItem(System system, NavRouteMessage.V1.Item item) {
        if (Objects.isNull(system.getEliteId())) {
            system.setEliteId(item.getSystemAddress());
        }

        if (Objects.isNull(system.getStarClass())) {
            system.setStarClass(item.getStarClass());
        }

        if (Objects.isNull(system.getCoordinate().x()) || Objects.isNull(system.getCoordinate().y()) || Objects.isNull(system.getCoordinate().z())) {
            system.setCoordinate(new Coordinate(item.getStarPos()[0], item.getStarPos()[1], item.getStarPos()[2]));
        }
        return system;
    }

    private CompletableFuture<System> saveSystem(System system) {
        return CompletableFuture.supplyAsync(() -> saveSystemPort.save(system));
    }

    private void sendResponse(System system) {
        loadSystemCoordinateRequestBySystemNamePort.load(system.getName()).parallelStream()
                .forEach(systemCoordinateRequest -> CompletableFuture.runAsync(() -> {
                    SystemCoordinatesResponse systemCoordinatesResponse = systemCoordinatesResponseMapper.map(system);
                    String stringJson = objectMapper.valueToTree(systemCoordinatesResponse).toString();
                    KafkaMessage message = new KafkaMessage(systemCoordinateRequest.requestingModule() + TOPIC, stringJson);

                    if (sendKafkaMessagePort.send(message)) {
                        deleteSystemCoordinateRequestPort.delete(system.getName(), systemCoordinateRequest.requestingModule());
                    }
                }));

    }
}
