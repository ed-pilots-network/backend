package io.edpn.backend.commodityfinder.domain.usecase;

import io.edpn.backend.commodityfinder.infrastructure.kafka.processor.StationArrivalDistanceResponseMessageProcessor;

public interface ReceiveStationArrivalDistanceResponseUseCase {
    void receive(StationArrivalDistanceResponseMessageProcessor.StationArrivalDistanceResponse message);
}
