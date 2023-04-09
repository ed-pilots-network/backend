package io.eddb.eddb2backend.application.service;

import io.eddb.eddb2backend.application.usecase.GetStationUsecase;
import io.eddb.eddb2backend.domain.model.Station;
import io.eddb.eddb2backend.domain.repository.StationRepository;
import java.util.Collection;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class GetStationService implements GetStationUsecase {
    private final StationRepository stationRepository;

    @Override
    @Transactional
    public Optional<Station> getById(Long id) {
        return stationRepository.findById(id);
    }

    @Override
    @Transactional
    public Collection<Station> getAll() {
        return stationRepository.findAll();
    }
}
