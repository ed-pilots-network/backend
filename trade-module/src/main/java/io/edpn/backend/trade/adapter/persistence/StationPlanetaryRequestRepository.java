package io.edpn.backend.trade.adapter.persistence;


import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationPlanetaryRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.CreateStationPlanetaryRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.DeleteStationPlanetaryRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.ExistsStationPlanetaryRequestPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class StationPlanetaryRequestRepository implements CreateStationPlanetaryRequestPort, ExistsStationPlanetaryRequestPort, DeleteStationPlanetaryRequestPort {

    private final MybatisStationPlanetaryRequestRepository mybatisStationPlanetaryRequestRepository;

    @Override
    public void create(String systemName, String stationName) {
        mybatisStationPlanetaryRequestRepository.insert(systemName, stationName);
    }

    @Override
    public void delete(String systemName, String stationName) {
        mybatisStationPlanetaryRequestRepository.delete(systemName, stationName);
    }

    @Override
    public boolean exists(String systemName, String stationName) {
        return mybatisStationPlanetaryRequestRepository.exists(systemName, stationName);
    }
}
