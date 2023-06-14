package io.edpn.backend.commodityfinder.application.usecase;

import io.edpn.backend.commodityfinder.domain.model.Station;
import io.edpn.backend.commodityfinder.domain.model.System;
import io.edpn.backend.commodityfinder.domain.repository.StationRepository;
import io.edpn.backend.commodityfinder.domain.repository.SystemRepository;
import io.edpn.backend.commodityfinder.domain.usecase.ReceiveStationArrivalDistanceResponseUseCase;
import io.edpn.backend.commodityfinder.infrastructure.kafka.processor.StationArrivalDistanceResponseMessageProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Slf4j
public class DefaultReceiveStationArrivalDistanceResponseUseCase implements ReceiveStationArrivalDistanceResponseUseCase {

    private final SystemRepository systemRepository;
    private final StationRepository stationRepository;

    @Override
    public void receive(StationArrivalDistanceResponseMessageProcessor.StationArrivalDistanceResponse message) {
        String systemName = message.getSystemName();
        String stationName = message.getStationName();
        double arrivalDistance = message.getArrivalDistance();

        CompletableFuture<System> systemCompletableFuture = CompletableFuture.supplyAsync(() -> systemRepository.findOrCreateByName(systemName));

        // get station
        CompletableFuture<Station> stationCompletableFuture = CompletableFuture.supplyAsync(() -> stationRepository.findOrCreateBySystemAndStationName(systemCompletableFuture.join(), stationName));
        stationCompletableFuture.whenComplete((station, throwable) -> {
            if (throwable != null) {
                log.error("Exception occurred in retrieving station", throwable);
            } else {
                station.setArrivalDistance(arrivalDistance);
            }
        });

        stationRepository.update(stationCompletableFuture.join());
    }
}
