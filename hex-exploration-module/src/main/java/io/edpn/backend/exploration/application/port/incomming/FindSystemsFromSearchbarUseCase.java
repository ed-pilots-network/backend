package io.edpn.backend.exploration.application.port.incomming;

import io.edpn.backend.exploration.application.dto.SystemDto;

import java.util.List;

public interface FindSystemsFromSearchbarUseCase {

    List<SystemDto> findSystemsFromSearchBar(String subString, int amount);
}
