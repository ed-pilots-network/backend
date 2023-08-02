package io.edpn.backend.exploration.application.service;

import io.edpn.backend.exploration.application.dto.SystemDto;
import io.edpn.backend.exploration.application.dto.mapper.SystemDtoMapper;
import io.edpn.backend.exploration.application.port.incomming.FindSystemsFromSearchbarUseCase;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemsByNameContainingPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class SystemControllerService implements FindSystemsFromSearchbarUseCase {

    private final LoadSystemsByNameContainingPort loadSystemsByNameContainingPort;
    private final SystemDtoMapper systemDtoMapper;

    @Override
    public List<SystemDto> findSystemsFromSearchBar(String subString, int amount) {
        return loadSystemsByNameContainingPort.load(subString, amount)
                .stream()
                .map(systemDtoMapper::map)
                .toList();
    }
}
