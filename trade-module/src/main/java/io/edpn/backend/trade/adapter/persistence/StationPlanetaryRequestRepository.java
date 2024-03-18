package io.edpn.backend.trade.adapter.persistence;


import io.edpn.backend.trade.application.domain.intermodulecommunication.StationDataRequest;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisStationDataRequestEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationPlanetaryRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.CreateIfNotExistsStationPlanetaryRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.DeleteStationPlanetaryRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.ExistsStationPlanetaryRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.LoadAllStationPlanetaryRequestsPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class StationPlanetaryRequestRepository implements CreateIfNotExistsStationPlanetaryRequestPort, ExistsStationPlanetaryRequestPort, DeleteStationPlanetaryRequestPort, LoadAllStationPlanetaryRequestsPort {

    private final MybatisStationPlanetaryRequestRepository mybatisStationPlanetaryRequestRepository;
    private final MybatisStationDataRequestEntityMapper mybatisStationDataRequestEntityMapper;

    @Override
    public void createIfNotExists(String systemName, String stationName) {
        mybatisStationPlanetaryRequestRepository.insertIfNotExists(systemName, stationName);
    }

    @Override
    public void delete(String systemName, String stationName) {
        mybatisStationPlanetaryRequestRepository.delete(systemName, stationName);
    }

    @Override
    public boolean exists(String systemName, String stationName) {
        return mybatisStationPlanetaryRequestRepository.exists(systemName, stationName);
    }

    @Override
    public List<StationDataRequest> loadAll() {
        return mybatisStationPlanetaryRequestRepository.findAll().stream()
                .map(mybatisStationDataRequestEntityMapper::map)
                .toList();
    }
}
