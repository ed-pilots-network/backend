package io.edpn.backend.exploration.application.dto.persistence.entity.mapper;

import io.edpn.backend.exploration.application.domain.Body;
import io.edpn.backend.exploration.application.dto.persistence.entity.BodyEntity;

public interface BodyEntityMapper<T extends BodyEntity> {
    Body map(BodyEntity bodyEntity);

    T map(Body body);
}
