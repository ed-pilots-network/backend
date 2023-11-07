package io.edpn.backend.exploration.application.dto.persistence.entity.mapper;

import io.edpn.backend.exploration.application.domain.Ring;
import io.edpn.backend.exploration.application.dto.persistence.entity.RingEntity;

public interface RingEntityMapper<T extends RingEntity> {
    Ring map(RingEntity ringEntity);

    T map(Ring ring);
}
