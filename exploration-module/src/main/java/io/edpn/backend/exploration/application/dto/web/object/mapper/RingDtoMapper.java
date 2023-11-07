package io.edpn.backend.exploration.application.dto.web.object.mapper;

import io.edpn.backend.exploration.application.domain.Ring;
import io.edpn.backend.exploration.application.dto.web.object.RingDto;

public interface RingDtoMapper {
    RingDto map(Ring ring);
}
