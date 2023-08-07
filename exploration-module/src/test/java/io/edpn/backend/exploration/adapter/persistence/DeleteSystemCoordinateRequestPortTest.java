package io.edpn.backend.exploration.adapter.persistence;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemCoordinateRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisSystemCoordinateRequestEntityMapper;
import io.edpn.backend.exploration.application.port.outgoing.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.util.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteSystemCoordinateRequestPortTest {

    @Mock
    private MybatisSystemCoordinateRequestRepository mybatisSystemCoordinateRequestRepository;

    @Mock
    private MybatisSystemCoordinateRequestEntityMapper mybatisSystemCoordinateRequestEntityMapper;

    private DeleteSystemCoordinateRequestPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemCoordinateRequestRepository(mybatisSystemCoordinateRequestRepository, mybatisSystemCoordinateRequestEntityMapper);
    }

    @Test
    void delete_shouldDeleteFromRepository() {

        String systemName = "system";
        Module requestingModule = mock(Module.class);


        underTest.delete(systemName, requestingModule);


        verify(mybatisSystemCoordinateRequestRepository).delete(eq(new MybatisSystemCoordinateRequestEntity(systemName, requestingModule)));
    }
}