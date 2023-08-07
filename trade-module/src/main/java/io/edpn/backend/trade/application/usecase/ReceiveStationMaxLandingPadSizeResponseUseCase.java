package io.edpn.backend.trade.application.usecase;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationMaxLandingPadSizeResponse;
import io.edpn.backend.trade.domain.model.LandingPadSize;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.domain.repository.StationRepository;
import io.edpn.backend.trade.domain.repository.SystemRepository;
import io.edpn.backend.trade.domain.usecase.ReceiveDataRequestResponseUseCase;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class ReceiveStationMaxLandingPadSizeResponseUseCase implements ReceiveDataRequestResponseUseCase<StationMaxLandingPadSizeResponse> {

    private final SystemRepository systemRepository;
    private final StationRepository stationRepository;

    @Override
    public void receive(StationMaxLandingPadSizeResponse message) {
        String systemName = message.systemName();
        String stationName = message.stationName();
        LandingPadSize landingPadSize = LandingPadSize.valueOf(message.maxLandingPadSize());

        CompletableFuture<System> systemCompletableFuture = CompletableFuture.supplyAsync(() -> systemRepository.findOrCreateByName(systemName));

        // get station
        CompletableFuture<Station> stationCompletableFuture = CompletableFuture.supplyAsync(() -> stationRepository.findOrCreateBySystemAndStationName(systemCompletableFuture.join(), stationName));
        stationCompletableFuture.whenComplete((station, throwable) -> {
            if (throwable != null) {
                log.error("Exception occurred in retrieving station", throwable);
            } else {
                station.setMaxLandingPadSize(landingPadSize);
            }
        });

        stationRepository.update(stationCompletableFuture.join());
    }
}
