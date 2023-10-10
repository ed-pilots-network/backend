package io.edpn.backend.exploration.application.dto.web.object.mapper;

import io.edpn.backend.exploration.application.domain.Star;
import io.edpn.backend.exploration.application.dto.web.object.StarDto;

public interface StarDtoMapper {
    StarDto map(Star star);
}
