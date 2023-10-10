package io.edpn.backend.exploration.application.dto.web.object.mapper;

import io.edpn.backend.exploration.application.domain.Body;
import io.edpn.backend.exploration.application.dto.web.object.BodyDto;

public interface BodyDtoMapper {
    BodyDto map(Body body);
}
