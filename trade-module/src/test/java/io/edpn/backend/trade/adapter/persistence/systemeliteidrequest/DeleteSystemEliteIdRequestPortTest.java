package io.edpn.backend.trade.adapter.persistence.systemeliteidrequest;

import io.edpn.backend.trade.adapter.persistence.SystemEliteIdRequestRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemEliteIdRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeleteSystemEliteIdRequestPortTest {

    @Mock
    private MybatisSystemEliteIdRequestRepository mybatisSystemEliteIdRequestRepository;

    private DeleteSystemEliteIdRequestPort underTest;

    @BeforeEach
    public void setup() {
        underTest = new SystemEliteIdRequestRepository(mybatisSystemEliteIdRequestRepository);
    }

    @Test
    public void testCreate() {
        String systemName = "someName";

        underTest.delete(systemName);

        verify(mybatisSystemEliteIdRequestRepository).delete(systemName);
    }


}
