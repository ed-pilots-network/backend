package io.edpn.backend.trade.application.usecase;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationRequireOdysseyResponse;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.domain.repository.StationRepository;
import io.edpn.backend.trade.domain.repository.SystemRepository;
import io.edpn.backend.trade.domain.usecase.ReceiveDataRequestResponseUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Slf4j
public class ReceiveStationRequireOdysseyResponseUseCase implements ReceiveDataRequestResponseUseCase<StationRequireOdysseyResponse> {

    private final SystemRepository systemRepository;
    private final StationRepository stationRepository;

    @Override
    public void receive(StationRequireOdysseyResponse message) {
        String systemName = message.systemName();
        String stationName = message.stationName();
        boolean requireOdyssey = message.requireOdyssey();

        CompletableFuture<System> systemCompletableFuture = CompletableFuture.supplyAsync(() -> systemRepository.findOrCreateByName(systemName));

        // get station
        CompletableFuture<Station> stationCompletableFuture = CompletableFuture.supplyAsync(() -> stationRepository.findOrCreateBySystemAndStationName(systemCompletableFuture.join(), stationName));
        stationCompletableFuture.whenComplete((station, throwable) -> {
            if (throwable != null) {
                log.error("Exception occurred in retrieving station", throwable);
            } else {
                station.setRequireOdyssey(requireOdyssey);
            }
        });

        stationRepository.update(stationCompletableFuture.join());
    }
}
