package io.edpn.backend.trade.adapter.persistence;


import io.edpn.backend.trade.application.domain.intermodulecommunication.StationDataRequest;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisStationDataRequestEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationRequireOdysseyRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.CreateIfNotExistsStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.DeleteStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.ExistsStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.LoadAllStationRequireOdysseyRequestsPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class StationRequireOdysseyRequestRepository implements CreateIfNotExistsStationRequireOdysseyRequestPort, ExistsStationRequireOdysseyRequestPort, DeleteStationRequireOdysseyRequestPort, LoadAllStationRequireOdysseyRequestsPort {

    private final MybatisStationRequireOdysseyRequestRepository mybatisStationRequireOdysseyRequestRepository;
    private final MybatisStationDataRequestEntityMapper mybatisStationDataRequestEntityMapper;

    @Override
    public void createIfNotExists(String systemName, String stationName) {
        mybatisStationRequireOdysseyRequestRepository.insertIfNotExists(systemName, stationName);
    }

    @Override
    public void delete(String systemName, String stationName) {
        mybatisStationRequireOdysseyRequestRepository.delete(systemName, stationName);
    }

    @Override
    public boolean exists(String systemName, String stationName) {
        return mybatisStationRequireOdysseyRequestRepository.exists(systemName, stationName);
    }

    @Override
    public List<StationDataRequest> loadAll() {
        return mybatisStationRequireOdysseyRequestRepository.findAll().stream()
                .map(mybatisStationDataRequestEntityMapper::map)
                .toList();
    }
}
