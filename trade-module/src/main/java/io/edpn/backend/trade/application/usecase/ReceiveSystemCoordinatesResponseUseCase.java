package io.edpn.backend.trade.application.usecase;

import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.domain.repository.SystemRepository;
import io.edpn.backend.trade.domain.usecase.ReceiveDataRequestResponseUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class ReceiveSystemCoordinatesResponseUseCase implements ReceiveDataRequestResponseUseCase<SystemCoordinatesResponse> {

    private final SystemRepository systemRepository;

    @Override
    public void receive(SystemCoordinatesResponse message) {
        String systemName = message.getSystemName();
        double xCoordinate = message.getXCoordinate();
        double yCoordinate = message.getYCoordinate();
        double zCoordinate = message.getZCoordinate();

        CompletableFuture<System> systemCompletableFuture = CompletableFuture.supplyAsync(() -> systemRepository.findOrCreateByName(systemName))
                .whenComplete((system, throwable) -> {
                    if (throwable != null) {
                        log.error("Exception occurred in retrieving system", throwable);
                    } else {
                        system.setXCoordinate(xCoordinate);
                        system.setYCoordinate(yCoordinate);
                        system.setZCoordinate(zCoordinate);
                    }
                });

        systemRepository.update(systemCompletableFuture.join());
    }
}
