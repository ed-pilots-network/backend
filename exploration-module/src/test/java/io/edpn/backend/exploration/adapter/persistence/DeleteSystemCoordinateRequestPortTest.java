package io.edpn.backend.exploration.adapter.persistence;

import io.edpn.backend.exploration.adapter.persistence.entity.SystemCoordinateRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.SystemCoordinateRequestEntityMapper;
import io.edpn.backend.exploration.application.port.outgoing.DeleteSystemCoordinateRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteSystemCoordinateRequestPortTest {

    @Mock
    private MybatisSystemCoordinateRequestRepository mybatisSystemCoordinateRequestRepository;

    @Mock
    private SystemCoordinateRequestEntityMapper systemCoordinateRequestEntityMapper;

    private DeleteSystemCoordinateRequestPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemCoordinateRequestRepository(mybatisSystemCoordinateRequestRepository, systemCoordinateRequestEntityMapper);
    }

    @Test
    void delete_shouldDeleteFromRepository() {

        String systemName = "system";
        String requestingModule = "module";


        underTest.delete(systemName, requestingModule);


        verify(mybatisSystemCoordinateRequestRepository).delete(new SystemCoordinateRequestEntity(systemName, requestingModule));
    }
}