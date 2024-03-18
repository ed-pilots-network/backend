package io.edpn.backend.trade.adapter.persistence;


import io.edpn.backend.trade.application.domain.intermodulecommunication.StationDataRequest;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisStationDataRequestEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationArrivalDistanceRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.CreateIfNotExistsStationArrivalDistanceRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.DeleteStationArrivalDistanceRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.ExistsStationArrivalDistanceRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.LoadAllStationArrivalDistanceRequestsPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class StationArrivalDistanceRequestRepository implements CreateIfNotExistsStationArrivalDistanceRequestPort, ExistsStationArrivalDistanceRequestPort, DeleteStationArrivalDistanceRequestPort, LoadAllStationArrivalDistanceRequestsPort {

    private final MybatisStationArrivalDistanceRequestRepository mybatisStationArrivalDistanceRequestRepository;
    private final MybatisStationDataRequestEntityMapper mybatisStationDataRequestEntityMapper;

    @Override
    public void createIfNotExists(String systemName, String stationName) {
        mybatisStationArrivalDistanceRequestRepository.insertIfNotExists(systemName, stationName);
    }

    @Override
    public void delete(String systemName, String stationName) {
        mybatisStationArrivalDistanceRequestRepository.delete(systemName, stationName);
    }

    @Override
    public boolean exists(String systemName, String stationName) {
        return mybatisStationArrivalDistanceRequestRepository.exists(systemName, stationName);
    }

    @Override
    public List<StationDataRequest> loadAll() {
        return mybatisStationArrivalDistanceRequestRepository.findAll().stream()
                .map(mybatisStationDataRequestEntityMapper::map)
                .toList();
    }
}
