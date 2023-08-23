package io.edpn.backend.trade.application.service;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationMaxLandingPadSizeResponse;
import io.edpn.backend.trade.application.domain.LandingPadSize;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import io.edpn.backend.trade.application.port.outgoing.station.LoadOrCreateBySystemAndStationNamePort;
import io.edpn.backend.trade.application.port.outgoing.station.UpdateStationPort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadOrCreateSystemByNamePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Slf4j
public class ReceiveStationMaxLandingPadSizeResponseService implements ReceiveKafkaMessageUseCase<StationMaxLandingPadSizeResponse> {
    
    private final LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort;
    private final LoadOrCreateBySystemAndStationNamePort loadOrCreateBySystemAndStationNamePort;
    private final UpdateStationPort updateStationPort;

    @Override
    public void receive(StationMaxLandingPadSizeResponse message) {
        String systemName = message.systemName();
        String stationName = message.stationName();
        LandingPadSize landingPadSize = LandingPadSize.valueOf(message.maxLandingPadSize());
        
        CompletableFuture<System> systemCompletableFuture = CompletableFuture.supplyAsync(() -> loadOrCreateSystemByNamePort.loadOrCreateSystemByName(systemName));
        
        // get station
        CompletableFuture<Station> stationCompletableFuture = CompletableFuture.supplyAsync(() -> loadOrCreateBySystemAndStationNamePort.loadOrCreateBySystemAndStationName(systemCompletableFuture.join(), stationName));
        stationCompletableFuture.whenComplete((station, throwable) -> {
            if (throwable != null) {
                log.error("Exception occurred in retrieving station", throwable);
            } else {
                station.setMaxLandingPadSize(landingPadSize);
            }
        });
        
        updateStationPort.update(stationCompletableFuture.join());
    }
}
