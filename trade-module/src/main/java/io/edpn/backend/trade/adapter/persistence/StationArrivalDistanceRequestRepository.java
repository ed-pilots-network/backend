package io.edpn.backend.trade.adapter.persistence;


import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationArrivalDistanceRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.CreateStationArrivalDistanceRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.DeleteStationArrivalDistanceRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.ExistsStationArrivalDistanceRequestPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class StationArrivalDistanceRequestRepository implements CreateStationArrivalDistanceRequestPort, ExistsStationArrivalDistanceRequestPort, DeleteStationArrivalDistanceRequestPort {

    private final MybatisStationArrivalDistanceRequestRepository mybatisStationArrivalDistanceRequestRepository;

    @Override
    public void create(String systemName, String stationName) {
        mybatisStationArrivalDistanceRequestRepository.insert(systemName, stationName);
    }

    @Override
    public void delete(String systemName, String stationName) {
        mybatisStationArrivalDistanceRequestRepository.delete(systemName, stationName);
    }

    @Override
    public boolean exists(String systemName, String stationName) {
        return mybatisStationArrivalDistanceRequestRepository.exists(systemName, stationName);
    }
}
