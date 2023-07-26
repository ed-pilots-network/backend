package io.edpn.backend.exploration.application.controller.v1;

import io.edpn.backend.exploration.application.dto.v1.SystemDTO;
import io.edpn.backend.exploration.application.mappers.v1.SystemDtoMapper;
import io.edpn.backend.exploration.domain.controller.v1.ExplorationModuleController;
import io.edpn.backend.exploration.domain.usecase.FindSystemsFromSearchbarUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DefaultExplorationModuleController implements ExplorationModuleController {

    private static final int DEFAULT_SIZE_LIMIT = 10;
    private final FindSystemsFromSearchbarUseCase findSystemsFromSearchbarUseCase;
    private final SystemDtoMapper systemDtoMapper;

    @Override
    public List<SystemDTO> findSystemsFromSearchBar(String subString, Integer amount) {
        return findSystemsFromSearchbarUseCase.findSystemsFromSearchBar(subString, Optional.ofNullable(amount).orElse(DEFAULT_SIZE_LIMIT))
                .stream()
                .map(systemDtoMapper::map)
                .toList();
    }
}
