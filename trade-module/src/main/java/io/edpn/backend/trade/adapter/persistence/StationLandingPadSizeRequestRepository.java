package io.edpn.backend.trade.adapter.persistence;


import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationLandingPadSizeRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.CreateStationLandingPadSizeRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.DeleteStationLandingPadSizeRequestPort;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.ExistsStationLandingPadSizeRequestPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class StationLandingPadSizeRequestRepository implements CreateStationLandingPadSizeRequestPort, ExistsStationLandingPadSizeRequestPort, DeleteStationLandingPadSizeRequestPort {

    private final MybatisStationLandingPadSizeRequestRepository mybatisStationLandingPadSizeRequestRepository;

    @Override
    public void create(String systemName, String stationName) {
        mybatisStationLandingPadSizeRequestRepository.insert(systemName, stationName);
    }

    @Override
    public void delete(String systemName, String stationName) {
        mybatisStationLandingPadSizeRequestRepository.delete(systemName, stationName);
    }

    @Override
    public boolean exists(String systemName, String stationName) {
        return mybatisStationLandingPadSizeRequestRepository.exists(systemName, stationName);
    }
}
