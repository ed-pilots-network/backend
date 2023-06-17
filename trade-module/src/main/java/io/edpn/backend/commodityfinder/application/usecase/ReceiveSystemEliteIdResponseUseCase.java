package io.edpn.backend.commodityfinder.application.usecase;

import io.edpn.backend.commodityfinder.domain.model.System;
import io.edpn.backend.commodityfinder.domain.repository.SystemRepository;
import io.edpn.backend.commodityfinder.domain.usecase.ReceiveDataRequestResponseUseCase;
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
        String systemName = message.getSystemName();
        long eliteId = message.getEliteId();

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
