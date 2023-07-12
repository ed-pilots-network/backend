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
        SystemCoordinatesResponse message = new SystemCoordinatesResponse();
        message.setSystemName("system");
        message.setXCoordinate(1.0);
        message.setYCoordinate(2.0);
        message.setZCoordinate(3.0);

        System system = new System();
        system.setName("system");
        when(systemRepository.findOrCreateByName(anyString())).thenReturn(system);

        underTest.receive(message);

        verify(systemRepository, times(1)).findOrCreateByName(anyString());
        verify(systemRepository, times(1)).update(any());

        assert(system.getXCoordinate() == 1.0);
        assert(system.getYCoordinate() == 2.0);
        assert(system.getZCoordinate() == 3.0);
    }
}
