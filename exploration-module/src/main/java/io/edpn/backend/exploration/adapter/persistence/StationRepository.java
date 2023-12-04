package io.edpn.backend.exploration.adapter.persistence;


import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.exploration.application.domain.Station;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.exploration.application.port.outgoing.station.LoadStationPort;
import io.edpn.backend.exploration.application.port.outgoing.station.SaveOrUpdateStationPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class StationRepository implements SaveOrUpdateStationPort, LoadStationPort {

    private final MybatisStationRepository mybatisStationRepository;
    private final StationEntityMapper<MybatisStationEntity> stationEntityMapper;

    @Override
    public Station saveOrUpdate(Station station) {
        return stationEntityMapper.map(mybatisStationRepository.insertOrUpdateOnConflict(stationEntityMapper.map(station)));
    }

    @Override
    public Optional<Station> load(String systemName, String name) {
        return mybatisStationRepository.findByIdentifier(systemName, name)
                .map(stationEntityMapper::map);
    }
}
