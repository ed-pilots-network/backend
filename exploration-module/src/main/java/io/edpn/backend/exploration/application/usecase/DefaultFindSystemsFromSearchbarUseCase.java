package io.edpn.backend.exploration.application.usecase;

import io.edpn.backend.exploration.domain.model.System;
import io.edpn.backend.exploration.domain.repository.SystemRepository;
import io.edpn.backend.exploration.domain.usecase.FindSystemsFromSearchbarUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class DefaultFindSystemsFromSearchbarUseCase implements FindSystemsFromSearchbarUseCase {

    private final SystemRepository systemRepository;

    @Override
    public List<System> findSystemsFromSearchBar(String name, int amount) {
        return systemRepository.findFromSearchbar(name, amount);
    }
}
