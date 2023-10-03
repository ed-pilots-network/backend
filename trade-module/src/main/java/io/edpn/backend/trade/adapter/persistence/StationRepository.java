package io.edpn.backend.trade.adapter.persistence;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationRepository;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.filter.FindStationFilter;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistenceFindStationFilterMapper;
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

    private final StationEntityMapper<MybatisStationEntity> mybatisStationEntityMapper;
    private final MybatisStationRepository mybatisStationRepository;
    private final PersistenceFindStationFilterMapper mybatisPersistenceFindStationFilterMapper;

    @Override
    public Station createOrLoad(Station station) {
        return mybatisStationEntityMapper.map(mybatisStationRepository.createOrUpdateOnConflict(mybatisStationEntityMapper.map(station)));
    }

    @Override
    public Optional<Station> loadById(UUID uuid) {
        return mybatisStationRepository.findById(uuid)
                .map(mybatisStationEntityMapper::map);
    }

    @Override
    public Station update(Station station) {
        var entity = mybatisStationEntityMapper.map(station);
        mybatisStationRepository.update(entity);

        return loadById(entity.getId())
                .orElseThrow(() -> new DatabaseEntityNotFoundException("station with id: " + entity.getId() + " could not be found after update"));
    }

    @Override
    public List<Station> loadByFilter(FindStationFilter findStationFilter) {
        return mybatisStationRepository.findByFilter(mybatisPersistenceFindStationFilterMapper.map(findStationFilter))
                .stream()
                .map(mybatisStationEntityMapper::map)
                .toList();
    }
}
