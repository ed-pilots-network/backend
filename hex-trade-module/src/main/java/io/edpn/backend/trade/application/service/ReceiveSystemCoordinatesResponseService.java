package io.edpn.backend.trade.application.service;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import io.edpn.backend.trade.application.port.outgoing.system.LoadOrCreateSystemByNamePort;
import io.edpn.backend.trade.application.port.outgoing.system.UpdateSystemPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Slf4j
public class ReceiveSystemCoordinatesResponseService implements ReceiveKafkaMessageUseCase<SystemCoordinatesResponse> {

    private final LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort;
    private final UpdateSystemPort updateSystemPort;

    @Override
    public void receive(SystemCoordinatesResponse message) {
        String systemName = message.getSystemName();
        double xCoordinate = message.getXCoordinate();
        double yCoordinate = message.getYCoordinate();
        double zCoordinate = message.getZCoordinate();

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
    }
}
