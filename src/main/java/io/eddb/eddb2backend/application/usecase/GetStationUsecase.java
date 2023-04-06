package io.eddb.eddb2backend.application.usecase;

import io.eddb.eddb2backend.domain.model.Station;
import java.util.Collection;
import java.util.Optional;

public interface GetStationUsecase {

    Optional<Station> getById(Long id);

    Collection<Station> getAll();
}
