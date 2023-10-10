package io.edpn.backend.exploration.application.port.incomming;

import io.edpn.backend.exploration.application.dto.web.object.SystemDto;

import java.util.List;

public interface FindSystemsByNameContainingUseCase {

    List<SystemDto> findSystemsByNameContaining(String subString, int amount);
}
