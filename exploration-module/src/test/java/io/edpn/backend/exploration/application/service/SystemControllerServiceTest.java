package io.edpn.backend.exploration.application.service;

import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.SystemDto;
import io.edpn.backend.exploration.application.dto.mapper.SystemDtoMapper;
import io.edpn.backend.exploration.application.port.incomming.FindSystemsFromSearchbarUseCase;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemsByNameContainingPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SystemControllerServiceTest {

    @Mock
    private LoadSystemsByNameContainingPort loadSystemsByNameContainingPort;
    @Mock
    private SystemDtoMapper systemDtoMapper;

    private FindSystemsFromSearchbarUseCase underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemControllerService(loadSystemsByNameContainingPort, systemDtoMapper);
    }

    @Test
    void testFindSystemsFromSearchBar_shouldReturnListOfSystemDto() {
        
        String subString = "sub";
        int amount = 10;
        System system1 = mock(System.class);
        System system2 = mock(System.class);
        SystemDto systemDto1 = mock(SystemDto.class);
        SystemDto systemDto2 = mock(SystemDto.class);
        when(loadSystemsByNameContainingPort.load(subString, amount)).thenReturn(List.of(system1, system2));
        when(systemDtoMapper.map(system1)).thenReturn(systemDto1);
        when(systemDtoMapper.map(system2)).thenReturn(systemDto2);


        List<SystemDto> result = underTest.findSystemsFromSearchBar(subString, amount);


        verify(loadSystemsByNameContainingPort).load(subString, amount);
        verify(systemDtoMapper).map(system1);
        verify(systemDtoMapper).map(system2);
        assertThat(result, hasSize(2));
        assertThat(result, containsInAnyOrder(systemDto1, systemDto2));
    }
}