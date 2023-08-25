package io.edpn.backend.trade.adapter.persistence;


import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationRequireOdysseyRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.CreateStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.DeleteStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.ExistsStationRequireOdysseyRequestPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class StationRequireOdysseyRequestRepository implements CreateStationRequireOdysseyRequestPort, ExistsStationRequireOdysseyRequestPort, DeleteStationRequireOdysseyRequestPort {

    private final MybatisStationRequireOdysseyRequestRepository mybatisStationRequireOdysseyRequestRepository;

    @Override
    public void create(String systemName, String stationName) {
        mybatisStationRequireOdysseyRequestRepository.insert(systemName, stationName);
    }

    @Override
    public void delete(String systemName, String stationName) {
        mybatisStationRequireOdysseyRequestRepository.delete(systemName, stationName);
    }

    @Override
    public boolean exists(String systemName, String stationName) {
        return mybatisStationRequireOdysseyRequestRepository.exists(systemName, stationName);
    }
}
