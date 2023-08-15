package io.edpn.backend.exploration.adapter.persistence.systemeliteidrequest;

import io.edpn.backend.exploration.adapter.persistence.MybatisSystemEliteIdRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.SystemEliteIdRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEliteIdRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisSystemEliteIdRequestEntityMapper;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
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
class DeleteSystemEliteIdRequestPortTest {

    @Mock
    private MybatisSystemEliteIdRequestRepository mybatisSystemEliteIdRequestRepository;

    @Mock
    private MybatisSystemEliteIdRequestEntityMapper mybatisSystemEliteIdRequestEntityMapper;

    private DeleteSystemEliteIdRequestPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemEliteIdRequestRepository(mybatisSystemEliteIdRequestRepository, mybatisSystemEliteIdRequestEntityMapper);
    }

    @Test
    void delete_shouldDeleteFromRepository() {

        String systemName = "system";
        Module requestingModule = mock(Module.class);


        underTest.delete(systemName, requestingModule);


        verify(mybatisSystemEliteIdRequestRepository).delete(eq(new MybatisSystemEliteIdRequestEntity(systemName, requestingModule)));
    }
}