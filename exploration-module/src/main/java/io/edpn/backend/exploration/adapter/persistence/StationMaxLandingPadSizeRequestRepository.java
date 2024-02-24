package io.edpn.backend.exploration.adapter.persistence;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationMaxLandingPadSizeRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisStationMaxLandingPadSizeRequestEntityMapper;
import io.edpn.backend.exploration.application.domain.StationMaxLandingPadSizeRequest;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.CreateIfNotExistsStationMaxLandingPadSizeRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.DeleteStationMaxLandingPadSizeRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.LoadAllStationMaxLandingPadSizeRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.LoadStationMaxLandingPadSizeRequestByIdentifierPort;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.LoadStationMaxLandingPadSizeRequestPort;
import io.edpn.backend.util.Module;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class StationMaxLandingPadSizeRequestRepository implements CreateIfNotExistsStationMaxLandingPadSizeRequestPort, LoadStationMaxLandingPadSizeRequestPort, LoadStationMaxLandingPadSizeRequestByIdentifierPort, LoadAllStationMaxLandingPadSizeRequestPort, DeleteStationMaxLandingPadSizeRequestPort {

    private final MybatisStationMaxLandingPadSizeRequestRepository mybatisStationMaxLandingPadSizeRequestRepository;
    private final MybatisStationMaxLandingPadSizeRequestEntityMapper mybatisStationMaxLandingPadSizeRequestEntityMapper;

    @Override
    public void createIfNotExists(StationMaxLandingPadSizeRequest stationMaxLandingPadSizeRequest) {
        mybatisStationMaxLandingPadSizeRequestRepository.insertIfNotExists(mybatisStationMaxLandingPadSizeRequestEntityMapper.map(stationMaxLandingPadSizeRequest));
    }

    @Override
    public void delete(String systemName, String stationName, Module requestingModule) {
        mybatisStationMaxLandingPadSizeRequestRepository.delete(new MybatisStationMaxLandingPadSizeRequestEntity(systemName, stationName, requestingModule));
    }

    @Override
    public List<StationMaxLandingPadSizeRequest> loadByIdentifier(String systemName, String stationName) {
        return mybatisStationMaxLandingPadSizeRequestRepository.findByIdentifier(systemName, stationName).stream()
                .map(mybatisStationMaxLandingPadSizeRequestEntityMapper::map)
                .toList();
    }

    @Override
    public List<StationMaxLandingPadSizeRequest> loadAll() {
        return mybatisStationMaxLandingPadSizeRequestRepository.findAll().stream()
                .map(mybatisStationMaxLandingPadSizeRequestEntityMapper::map)
                .toList();
    }

    @Override
    public Optional<StationMaxLandingPadSizeRequest> load(StationMaxLandingPadSizeRequest stationMaxLandingPadSizeRequest) {
        return mybatisStationMaxLandingPadSizeRequestRepository.find(stationMaxLandingPadSizeRequest.requestingModule(), stationMaxLandingPadSizeRequest.systemName(), stationMaxLandingPadSizeRequest.stationName())
                .map(mybatisStationMaxLandingPadSizeRequestEntityMapper::map);
    }
}
