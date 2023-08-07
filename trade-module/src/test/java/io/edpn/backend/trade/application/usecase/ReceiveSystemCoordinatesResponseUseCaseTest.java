package io.edpn.backend.trade.application.usecase;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.domain.repository.SystemRepository;
import io.edpn.backend.trade.domain.usecase.ReceiveDataRequestResponseUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReceiveSystemCoordinatesResponseUseCaseTest {

    @Mock
    private SystemRepository systemRepository;

    private ReceiveDataRequestResponseUseCase<SystemCoordinatesResponse> underTest;

    @BeforeEach
    public void setUp() {
        underTest = new ReceiveSystemCoordinatesResponseUseCase(systemRepository);
    }

    @Test
    public void shouldReceiveSystemCoordinatesResponse() {
        SystemCoordinatesResponse message = new SystemCoordinatesResponse(
                "system", 1.0d, 2.0d, 3.0d
        );

        System system = System.builder()
                .name("system")
                .build();
        when(systemRepository.findOrCreateByName("system")).thenReturn(system);

        underTest.receive(message);

        verify(systemRepository, times(1)).findOrCreateByName(anyString());
        verify(systemRepository, times(1)).update(any());

        assertEquals(1.0d, system.getXCoordinate());
        assertEquals(2.0d, system.getYCoordinate());
        assertEquals(3.0d, system.getZCoordinate());
    }
}
