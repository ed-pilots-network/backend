package io.edpn.backend.exploration.application.service;

import io.edpn.backend.exploration.application.domain.exception.ValidationException;
import io.edpn.backend.exploration.application.dto.SystemDto;
import io.edpn.backend.exploration.application.dto.mapper.SystemDtoMapper;
import io.edpn.backend.exploration.application.port.incomming.FindSystemsByNameContainingUseCase;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemsByNameContainingPort;
import io.edpn.backend.exploration.application.validation.LoadByNameContainingValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class SystemControllerService implements FindSystemsByNameContainingUseCase {

    private final LoadSystemsByNameContainingPort loadSystemsByNameContainingPort;
    private final LoadByNameContainingValidator loadByNameContainingValidator;
    private final SystemDtoMapper systemDtoMapper;

    @Override
    public List<SystemDto> findSystemsByNameContaining(String subString, int amount) {
        Optional<ValidationException> validationResult = loadByNameContainingValidator.validate(subString, amount);
        if (validationResult.isPresent()) {
            throw validationResult.get();
        }

        return loadSystemsByNameContainingPort.loadByNameContaining(subString, amount)
                .stream()
                .map(systemDtoMapper::map)
                .toList();
    }
}
