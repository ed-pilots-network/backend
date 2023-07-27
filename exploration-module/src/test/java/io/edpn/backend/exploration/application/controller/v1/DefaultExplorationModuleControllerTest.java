package io.edpn.backend.exploration.application.controller.v1;

import io.edpn.backend.exploration.domain.usecase.FindSystemsFromSearchbarUseCase;
import io.edpn.backend.exploration.domain.controller.v1.ExplorationModuleController;
import io.edpn.backend.exploration.domain.dto.v1.SystemDto;
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
public class DefaultExplorationModuleControllerTest {

    @Mock
    private FindSystemsFromSearchbarUseCase findSystemsFromSearchbarUseCase;

    private ExplorationModuleController underTest;

    @BeforeEach
    void setUp() {
        underTest = new DefaultExplorationModuleController(findSystemsFromSearchbarUseCase);
    }

    @Test
    void shouldFindSystemsFromSearchBar() {
        String systemName = "System Name";
        int amount = 5;
        SystemDto mockSystemDto = mock(SystemDto.class);

        when(findSystemsFromSearchbarUseCase.findSystemsFromSearchBar(systemName, amount)).thenReturn(List.of(mockSystemDto));

        List<SystemDto> result = underTest.findSystemsFromSearchBar(systemName, amount);

        assertThat(result, equalTo(List.of(mockSystemDto)));
    }

    @Test
    void shouldFindSystemsFromSearchBarWithDefaultAmount() {
        String systemName = "System Name";
        SystemDto mockSystemDto = mock(SystemDto.class);

        when(findSystemsFromSearchbarUseCase.findSystemsFromSearchBar(systemName, 10)).thenReturn(List.of(mockSystemDto));

        List<SystemDto> result = underTest.findSystemsFromSearchBar(systemName, null);

        assertThat(result, equalTo(List.of(mockSystemDto)));
    }
}
