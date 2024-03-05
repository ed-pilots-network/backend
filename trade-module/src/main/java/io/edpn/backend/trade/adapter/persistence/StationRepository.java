package io.edpn.backend.trade.adapter.persistence;

import io.edpn.backend.trade.adapter.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.FindStationFilterMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationRepository;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.filter.FindStationFilter;
import io.edpn.backend.trade.application.port.outgoing.station.CreateOrLoadStationPort;
import io.edpn.backend.trade.application.port.outgoing.station.LoadStationByIdPort;
import io.edpn.backend.trade.application.port.outgoing.station.LoadStationsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.station.UpdateStationPort;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class StationRepository implements CreateOrLoadStationPort, LoadStationByIdPort, UpdateStationPort, LoadStationsByFilterPort {

    private final StationEntityMapper stationEntityMapper;
    private final MybatisStationRepository mybatisStationRepository;
    private final FindStationFilterMapper findStationFilterMapper;

    @Override
    public Station createOrLoad(Station station) {
        return stationEntityMapper.map(mybatisStationRepository.createOrUpdateOnConflict(stationEntityMapper.map(station)));
    }

    @Override
    public Optional<Station> loadById(UUID uuid) {
        return mybatisStationRepository.findById(uuid)
                .map(stationEntityMapper::map);
    }

    @Override
    public Station update(Station station) {
        var entity = stationEntityMapper.map(station);
        mybatisStationRepository.update(entity);

        return loadById(entity.getId())
                .orElseThrow(() -> new DatabaseEntityNotFoundException("station with id: " + entity.getId() + " could not be found after update"));
    }

    @Override
    public List<Station> loadByFilter(FindStationFilter findStationFilter) {
        return mybatisStationRepository.findByFilter(findStationFilterMapper.map(findStationFilter))
                .stream()
                .map(stationEntityMapper::map)
                .toList();
    }
}
