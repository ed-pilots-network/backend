package io.edpn.backend.exploration.domain.usecase;

import io.edpn.backend.exploration.domain.dto.v1.SystemDto;

import java.util.List;

public interface FindSystemsFromSearchbarUseCase {

    List<SystemDto> findSystemsFromSearchBar(String subString, int amount);
}
