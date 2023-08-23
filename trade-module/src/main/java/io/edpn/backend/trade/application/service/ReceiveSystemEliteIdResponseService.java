package io.edpn.backend.trade.application.service;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemEliteIdResponse;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import io.edpn.backend.trade.application.port.outgoing.system.LoadOrCreateSystemByNamePort;
import io.edpn.backend.trade.application.port.outgoing.system.UpdateSystemPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Slf4j
public class ReceiveSystemEliteIdResponseService implements ReceiveKafkaMessageUseCase<SystemEliteIdResponse> {
    
    private final LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort;
    private final UpdateSystemPort updateSystemPort;

    @Override
    public void receive(SystemEliteIdResponse message) {
        String systemName = message.getSystemName();
        long eliteId = message.getEliteId();
        
        CompletableFuture<System> systemCompletableFuture = CompletableFuture.supplyAsync(() -> loadOrCreateSystemByNamePort.loadOrCreateSystemByName(systemName))
                .whenComplete((station, throwable) -> {
                    if (throwable != null) {
                        log.error("Exception occurred in retrieving system", throwable);
                    } else {
                        station.setEliteId(eliteId);
                    }
                });
        
        updateSystemPort.update(systemCompletableFuture.join());
    }
}
