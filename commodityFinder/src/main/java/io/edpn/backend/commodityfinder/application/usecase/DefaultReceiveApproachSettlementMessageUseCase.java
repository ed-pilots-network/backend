package io.edpn.backend.commodityfinder.application.usecase;

import io.edpn.backend.commodityfinder.domain.model.Station;
import io.edpn.backend.commodityfinder.domain.model.System;
import io.edpn.backend.commodityfinder.domain.repository.StationRepository;
import io.edpn.backend.commodityfinder.domain.repository.SystemRepository;
import io.edpn.backend.commodityfinder.domain.usecase.ReceiveApproachSettlementMessageUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.ApproachSettlement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Slf4j
public class DefaultReceiveApproachSettlementMessageUseCase implements ReceiveApproachSettlementMessageUseCase {

    private final SystemRepository systemRepository;
    private final StationRepository stationRepository;

    @Override
    @Transactional
    public void receive(ApproachSettlement.V1 message) {

        long start = java.lang.System.nanoTime();
        if (log.isDebugEnabled()) {
            log.debug("DefaultReceiveApproachSettlementMessageUseCase.receive -> ApproachSettlementMessage: " + message);
        }

        var updateTimestamp = message.getMessageTimeStamp();

        ApproachSettlement.V1.Message payload = message.getMessage();
        long marketId = payload.getMarketId();
        long systemEliteId = payload.getSystemAddress();
        String systemName = payload.getStarSystem();
        String stationName = payload.getName();
        double[] starPos = payload.getStarPos();

        // get system
        CompletableFuture<System> systemCompletableFuture = CompletableFuture.supplyAsync(() -> systemRepository.findOrCreateByName(systemName));
        systemCompletableFuture.whenComplete((system, throwable) -> {
            if (throwable != null) {
                log.error("Exception occurred in retrieving system", throwable);
            } else {
                if (Objects.isNull(system.getXCoordinate())) {
                    system.setXCoordinate(starPos[0]);
                }
                if (Objects.isNull(system.getYCoordinate())) {
                    system.setYCoordinate(starPos[1]);
                }
                if (Objects.isNull(system.getZCoordinate())) {
                    system.setZCoordinate(starPos[2]);
                }
                if (Objects.isNull(system.getEliteId())) {
                    system.setEliteId(systemEliteId);
                }
            }
        });

        // get station
        CompletableFuture<Station> stationCompletableFuture = CompletableFuture.supplyAsync(() -> stationRepository.findOrCreateBySystemAndStationName(systemCompletableFuture.copy().join(), stationName));
        stationCompletableFuture.whenComplete((station, throwable) -> {
            if (throwable != null) {
                log.error("Exception occurred in retrieving station", throwable);
            } else {
                if (Objects.isNull(station.getMarketId())) {
                    station.setMarketId(marketId);
                }
            }
        });

        systemRepository.update(systemCompletableFuture.join());
        stationRepository.update(stationCompletableFuture.join());

        if (log.isTraceEnabled()) {
            log.trace("DefaultReceiveApproachSettlementMessageUseCase.receive -> took " + (java.lang.System.nanoTime() - start) + " nanosecond");
        }

        log.info("DefaultReceiveApproachSettlementMessageUseCase.receive -> the message has been processed");
    }
}
