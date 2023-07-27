package io.edpn.backend.exploration.application.usecase;

import io.edpn.backend.exploration.application.mappers.v1.SystemDtoMapper;
import io.edpn.backend.exploration.domain.dto.v1.SystemDto;
import io.edpn.backend.exploration.domain.repository.SystemRepository;

import java.util.List;

import io.edpn.backend.exploration.domain.usecase.FindSystemsFromSearchbarUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class DefaultFindSystemsFromSearchbarUseCase implements FindSystemsFromSearchbarUseCase {

    private final SystemRepository systemRepository;
    private final SystemDtoMapper systemDtoMapper;


    @Override
    public List<SystemDto> findSystemsFromSearchBar(String subString, int amount) {
        return systemRepository.findFromSearchbar(subString, amount)
                .stream()
                .map(systemDtoMapper::map)
                .toList();
    }
}
