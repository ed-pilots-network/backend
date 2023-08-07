package io.edpn.backend.trade.application.usecase;

import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.domain.repository.SystemRepository;
import io.edpn.backend.trade.domain.usecase.ReceiveDataRequestResponseUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemEliteIdResponse;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class ReceiveSystemEliteIdResponseUseCase implements ReceiveDataRequestResponseUseCase<SystemEliteIdResponse> {

    private final SystemRepository systemRepository;

    @Override
    public void receive(SystemEliteIdResponse message) {
        String systemName = message.systemName();
        long eliteId = message.eliteId();

        CompletableFuture<System> systemCompletableFuture = CompletableFuture.supplyAsync(() -> systemRepository.findOrCreateByName(systemName))
                .whenComplete((station, throwable) -> {
                    if (throwable != null) {
                        log.error("Exception occurred in retrieving system", throwable);
                    } else {
                        station.setEliteId(eliteId);
                    }
                });

        systemRepository.update(systemCompletableFuture.join());
    }
}
