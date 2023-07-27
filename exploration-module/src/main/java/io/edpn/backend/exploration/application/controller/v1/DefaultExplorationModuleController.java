package io.edpn.backend.exploration.application.controller.v1;

import io.edpn.backend.exploration.domain.dto.v1.SystemDto;
import io.edpn.backend.exploration.domain.usecase.FindSystemsFromSearchbarUseCase;
import io.edpn.backend.exploration.domain.controller.v1.ExplorationModuleController;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DefaultExplorationModuleController implements ExplorationModuleController {

    private static final int DEFAULT_SIZE_LIMIT = 10;
    private final FindSystemsFromSearchbarUseCase findSystemsFromSearchbarUseCase;

    @Override
    public List<SystemDto> findSystemsFromSearchBar(String subString, Integer amount) {
        return findSystemsFromSearchbarUseCase.findSystemsFromSearchBar(subString, Optional.ofNullable(amount).orElse(DEFAULT_SIZE_LIMIT));
    }
}
