package io.edpn.backend.exploration.adapter.persistence;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationArrivalDistanceRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisStationArrivalDistanceRequestEntityMapper;
import io.edpn.backend.exploration.application.domain.StationArrivalDistanceRequest;
import io.edpn.backend.exploration.application.domain.StationMaxLandingPadSizeRequest;
import io.edpn.backend.exploration.application.port.outgoing.stationarrivaldistancerequest.CreateIfNotExistsStationArrivalDistanceRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.stationarrivaldistancerequest.DeleteStationArrivalDistanceRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.stationarrivaldistancerequest.LoadAllStationArrivalDistanceRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.stationarrivaldistancerequest.LoadStationArrivalDistanceRequestByIdentifierPort;
import io.edpn.backend.exploration.application.port.outgoing.stationarrivaldistancerequest.LoadStationArrivalDistanceRequestPort;
import io.edpn.backend.util.Module;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class StationArrivalDistanceRequestRepository implements
        CreateIfNotExistsStationArrivalDistanceRequestPort,
        LoadStationArrivalDistanceRequestPort,
        LoadStationArrivalDistanceRequestByIdentifierPort,
        LoadAllStationArrivalDistanceRequestPort,
        DeleteStationArrivalDistanceRequestPort {
    
    private final MybatisStationArrivalDistanceRequestRepository mybatisStationArrivalDistanceRequestRepository;
    private final MybatisStationArrivalDistanceRequestEntityMapper mybatisStationArrivalDistanceRequestEntityMapper;
    
    
    @Override
    public void createIfNotExists(StationArrivalDistanceRequest stationArrivalDistanceRequest) {
        mybatisStationArrivalDistanceRequestRepository.insertIfNotExists(mybatisStationArrivalDistanceRequestEntityMapper.map(stationArrivalDistanceRequest));
    
    }
    
    @Override
    public void delete(String systemName, String stationName, Module requestingModule) {
        mybatisStationArrivalDistanceRequestRepository.delete(new MybatisStationArrivalDistanceRequestEntity(systemName, stationName, requestingModule));
    }
    
    @Override
    public List<StationArrivalDistanceRequest> loadAll() {
        return null;
    }
    
    @Override
    public List<StationArrivalDistanceRequest> loadByIdentifier(String systemName, String stationName) {
        return mybatisStationArrivalDistanceRequestRepository.findByIdentifier(systemName, stationName).stream()
                .map(mybatisStationArrivalDistanceRequestEntityMapper::map)
                .toList();
    }
    
    @Override
    public Optional<StationArrivalDistanceRequest> load(StationMaxLandingPadSizeRequest stationMaxLandingPadSizeRequest) {
        return mybatisStationArrivalDistanceRequestRepository.find(stationMaxLandingPadSizeRequest.requestingModule(), stationMaxLandingPadSizeRequest.systemName(), stationMaxLandingPadSizeRequest.stationName())
                .map(mybatisStationArrivalDistanceRequestEntityMapper::map);
    }
}
