package io.edpn.backend.exploration.application.usecase;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.domain.model.RequestDataMessage;
import io.edpn.backend.exploration.domain.model.System;
import io.edpn.backend.exploration.domain.repository.RequestDataMessageRepository;
import io.edpn.backend.exploration.domain.repository.SystemCoordinateDataRequestRepository;
import io.edpn.backend.exploration.domain.repository.SystemRepository;
import io.edpn.backend.exploration.domain.usecase.ReceiveNavRouteMessageUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.NavRouteMessage;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Slf4j
public class DefaultReceiveNavRouteMessageUseCase implements ReceiveNavRouteMessageUseCase {

    private final SystemRepository systemRepository;
    private final SystemCoordinateDataRequestRepository systemCoordinateDataRequestRepository;
    private final RequestDataMessageRepository requestDataMessageRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void receive(NavRouteMessage.V1 message) {
        long start = java.lang.System.nanoTime();
        log.debug("DefaultReceiveNavRouteMessageUseCase.receive -> CommodityMessage: {}", message);

        //LocalDateTime updateTimestamp = message.getMessageTimeStamp();
        NavRouteMessage.V1.Message payload = message.getMessage();
        NavRouteMessage.V1.Item[] routeItems = payload.getItems();

        List<CompletableFuture<Void>> futures = Arrays.stream(routeItems)
                .map(this::process)
                .toList();

        // uncomment if we want to complete all inside the scope of the receive() method, otherwise they will complete eventually, assuming they don't indefinitely block or throw exceptions
        // CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        log.trace("DefaultReceiveNavRouteMessageUseCase.receive -> took {} nanosecond", java.lang.System.nanoTime() - start);
        log.info("DefaultReceiveNavRouteMessageUseCase.receive -> the message has been processed");
    }

    private CompletableFuture<Void> process(NavRouteMessage.V1.Item routeItem) {
        return CompletableFuture.supplyAsync(() -> systemRepository.findOrCreateByName(routeItem.getStarSystem()))
                .thenApply(system -> updateSystemFromItem(system, routeItem))
                .thenCompose(system -> CompletableFuture.supplyAsync(() -> systemRepository.update(system)))
                .thenAcceptAsync(this::sendCoordinateDataRequest);
    }

    private System updateSystemFromItem(System system, NavRouteMessage.V1.Item routeItem) {
        if (Objects.isNull(system.getEliteId())) {
            system.setEliteId(routeItem.getSystemAddress());
        }

        if (Objects.isNull(system.getStarClass())) {
            system.setStarClass(routeItem.getStarClass());
        }

        if (Objects.isNull(system.getXCoordinate()) || Objects.isNull(system.getYCoordinate()) || Objects.isNull(system.getZCoordinate())) {
            system.setXCoordinate(routeItem.getStarPos()[0]);
            system.setYCoordinate(routeItem.getStarPos()[1]);
            system.setZCoordinate(routeItem.getStarPos()[2]);
        }
        return system;
    }

    private void sendCoordinateDataRequest(System system) {
        systemCoordinateDataRequestRepository.findBySystemName(system.getName())
                .forEach(systemCoordinateDataRequest -> CompletableFuture.runAsync(() -> {
                    SystemCoordinatesResponse systemCoordinatesResponse = createSystemCoordinatesResponse(system);
                    JsonNode jsonNode = objectMapper.valueToTree(systemCoordinatesResponse);

                    RequestDataMessage requestDataMessage = RequestDataMessage.builder()
                            .topic(systemCoordinateDataRequest.getRequestingModule() + "StationArrivalDistanceDataRequest")
                            .message(jsonNode)
                            .build();

                    requestDataMessageRepository.sendToKafka(requestDataMessage);
                    systemCoordinateDataRequestRepository.delete(systemCoordinateDataRequest);
                }));
    }

    private SystemCoordinatesResponse createSystemCoordinatesResponse(System system) {
        SystemCoordinatesResponse systemCoordinatesResponse = new SystemCoordinatesResponse();
        systemCoordinatesResponse.setSystemName(system.getName());
        systemCoordinatesResponse.setXCoordinate(system.getXCoordinate());
        systemCoordinatesResponse.setYCoordinate(system.getYCoordinate());
        systemCoordinatesResponse.setZCoordinate(system.getZCoordinate());
        return systemCoordinatesResponse;
    }
}
