package io.edpn.backend.exploration.application.controller.v1;

import io.edpn.backend.exploration.application.dto.v1.SystemDTO;
import io.edpn.backend.exploration.application.mappers.v1.SystemDtoMapper;
import io.edpn.backend.exploration.domain.controller.v1.ExplorationModuleController;
import io.edpn.backend.exploration.domain.usecase.FindSystemsFromSearchbarUseCase;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultExplorationModuleController implements ExplorationModuleController {

    private final FindSystemsFromSearchbarUseCase findSystemsFromSearchbarUseCase;
    private final SystemDtoMapper systemDtoMapper;

    @Override
    public List<SystemDTO> findSystemsFromSearchBar(String name, Integer amount) {
        return findSystemsFromSearchbarUseCase.findSystemsFromSearchBar(name, Optional.ofNullable(amount).orElse(10))
                .stream()
                .map(systemDtoMapper::map)
                .toList();
    }
}
