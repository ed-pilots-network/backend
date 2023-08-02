package io.edpn.backend.exploration.adapter.web;

import io.edpn.backend.exploration.application.dto.SystemDto;
import io.edpn.backend.exploration.application.port.incomming.FindSystemsFromSearchbarUseCase;
import io.edpn.backend.exploration.application.port.incomming.SystemControllerPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SystemControllerPortTest {

    @Mock
    private FindSystemsFromSearchbarUseCase findSystemsFromSearchbarUseCase;

    private SystemControllerPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemController(findSystemsFromSearchbarUseCase);
    }


    @Test
    public void testFindSystemsFromSearchBar_shouldReturnSystems() {
        // Given
        String subString = "test";
        int amount = 10;
        SystemDto systemDto = mock(SystemDto.class);
        List<SystemDto> expectedSystems = Collections.singletonList(systemDto);
        when(findSystemsFromSearchbarUseCase.findSystemsFromSearchBar(subString, amount)).thenReturn(expectedSystems);

        // When
        List<SystemDto> actualSystems = underTest.findSystemsFromSearchBar(subString, amount);

        // Then
        assertThat(actualSystems, is(expectedSystems));
        verify(findSystemsFromSearchbarUseCase).findSystemsFromSearchBar(subString, amount);
    }
}