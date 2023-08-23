package io.edpn.backend.trade.application.service;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import io.edpn.backend.trade.application.port.outgoing.system.LoadOrCreateSystemByNamePort;
import io.edpn.backend.trade.application.port.outgoing.system.UpdateSystemPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Slf4j
public class ReceiveSystemCoordinatesResponseService implements ReceiveKafkaMessageUseCase<SystemCoordinatesResponse> {

    private final LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort;
    private final DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort;
    private final UpdateSystemPort updateSystemPort;

    @Override
    //TODO: VERIFY RECEIVING CORRECTLY
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
