package io.edpn.backend.exploration.application.dto.persistence.entity.mapper;

import io.edpn.backend.exploration.application.domain.Star;
import io.edpn.backend.exploration.application.dto.persistence.entity.StarEntity;

public interface StarEntityMapper<T extends StarEntity> {
    Star map(StarEntity starEntity);

    T map(Star star);
}
