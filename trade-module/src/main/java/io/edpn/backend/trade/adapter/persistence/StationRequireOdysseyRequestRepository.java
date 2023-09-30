package io.edpn.backend.trade.adapter.persistence;


import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationRequireOdysseyRequestRepository;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.StationDataRequestEntityMapper;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.CreateStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.DeleteStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.ExistsStationRequireOdysseyRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.LoadAllStationRequireOdysseyRequestsPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class StationRequireOdysseyRequestRepository implements CreateStationRequireOdysseyRequestPort, ExistsStationRequireOdysseyRequestPort, DeleteStationRequireOdysseyRequestPort, LoadAllStationRequireOdysseyRequestsPort {

    private final MybatisStationRequireOdysseyRequestRepository mybatisStationRequireOdysseyRequestRepository;
    private final StationDataRequestEntityMapper stationDataRequestEntityMapper;

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

    @Override
    public List<StationDataRequest> loadAll() {
        return mybatisStationRequireOdysseyRequestRepository.findAll().stream()
                .map(stationDataRequestEntityMapper::map)
                .toList();
    }
}
