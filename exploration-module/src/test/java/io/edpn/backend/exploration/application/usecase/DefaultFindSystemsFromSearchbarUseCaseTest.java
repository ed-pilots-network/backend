package io.edpn.backend.exploration.application.usecase;

import io.edpn.backend.exploration.domain.dto.v1.SystemDto;
import io.edpn.backend.exploration.application.mappers.v1.SystemDtoMapper;
import io.edpn.backend.exploration.domain.model.System;
import io.edpn.backend.exploration.domain.repository.SystemRepository;
import io.edpn.backend.exploration.domain.usecase.FindSystemsFromSearchbarUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultFindSystemsFromSearchbarUseCaseTest {


    @Mock
    private SystemRepository systemRepository;
    @Mock
    private SystemDtoMapper systemDtoMapper;

    private FindSystemsFromSearchbarUseCase underTest;

    @BeforeEach
    void setUp() {
        underTest = new DefaultFindSystemsFromSearchbarUseCase(systemRepository, systemDtoMapper);
    }

    @Test
    void shouldFindSystemsFromSearchBar() {
        String systemName = "System Name";
        int amount = 5;
        System mockSystem = mock(System.class);
        SystemDto mockSystemDto = mock(SystemDto.class);

        when(systemRepository.findFromSearchbar(systemName, amount)).thenReturn(List.of(mockSystem));
        when(systemDtoMapper.map(mockSystem)).thenReturn(mockSystemDto);

        List<SystemDto> result = underTest.findSystemsFromSearchBar(systemName, amount);

        assertThat(result, equalTo(List.of(mockSystemDto)));
    }
}