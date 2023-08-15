package io.edpn.backend.exploration.adapter.web;

import io.edpn.backend.exploration.application.dto.SystemDto;
import io.edpn.backend.exploration.application.port.incomming.FindSystemsByNameContainingUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SystemControllerPortTest {

    @Mock
    private FindSystemsByNameContainingUseCase findSystemsByNameContainingUseCase;
    @Mock
    private FindSystemsByNameContainingInputValidator findSystemsByNameContainingInputValidator;

    private SystemController underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemController(findSystemsByNameContainingUseCase, findSystemsByNameContainingInputValidator);
    }

    @Test
    public void testFindSystemsFromSearchBar_shouldThrowOnInvalidSubString() {
        String subString = null;
        int amount = 10;
        doThrow(new IllegalArgumentException("subString must not be null or empty"))
                .when(findSystemsByNameContainingInputValidator)
                .validateSubString(subString);

        assertThrows(IllegalArgumentException.class, () -> underTest.findByNameContaining(subString, amount));

        verify(findSystemsByNameContainingInputValidator).validateSubString(subString);
    }

    @Test
    public void testFindSystemsFromSearchBar_shouldThrowOnInvalidAmount() {
        String subString = "test";
        int amount = 1;
        doThrow(new IllegalArgumentException("Amount must not be strict positive"))
                .when(findSystemsByNameContainingInputValidator)
                .validateAmount(amount);

        assertThrows(IllegalArgumentException.class, () -> underTest.findByNameContaining(subString, amount));

        verify(findSystemsByNameContainingInputValidator).validateAmount(amount);
    }


    @Test
    public void testFindSystemsFromSearchBar_shouldReturnSystems() {

        String subString = "test";
        int amount = 10;
        SystemDto systemDto = mock(SystemDto.class);
        List<SystemDto> expectedSystems = List.of(systemDto);
        when(findSystemsByNameContainingUseCase.findSystemsByNameContaining(subString, amount)).thenReturn(expectedSystems);

        List<SystemDto> actualSystems = underTest.findByNameContaining(subString, amount);


        assertThat(actualSystems, is(expectedSystems));
        verify(findSystemsByNameContainingUseCase).findSystemsByNameContaining(subString, amount);
    }
}