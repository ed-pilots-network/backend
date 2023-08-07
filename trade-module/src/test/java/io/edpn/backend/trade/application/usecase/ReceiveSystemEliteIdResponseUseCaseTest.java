package io.edpn.backend.trade.application.usecase;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemEliteIdResponse;
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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReceiveSystemEliteIdResponseUseCaseTest {

    @Mock
    private SystemRepository systemRepository;

    private ReceiveDataRequestResponseUseCase<SystemEliteIdResponse> underTest;

    @BeforeEach
    public void setUp() {
        underTest = new ReceiveSystemEliteIdResponseUseCase(systemRepository);
    }

    @Test
    public void shouldReceiveSystemEliteIdResponse() {
        SystemEliteIdResponse message = new SystemEliteIdResponse(
                "system", 1234
        );

        System system = System.builder()
                .name("system")
                .build();
        when(systemRepository.findOrCreateByName("system")).thenReturn(system);

        underTest.receive(message);

        verify(systemRepository, times(1)).findOrCreateByName(anyString());
        verify(systemRepository, times(1)).update(any());

        assertEquals(1234, system.getEliteId());
    }
}
