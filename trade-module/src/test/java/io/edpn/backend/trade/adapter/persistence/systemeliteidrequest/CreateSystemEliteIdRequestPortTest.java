package io.edpn.backend.trade.adapter.persistence.systemeliteidrequest;

import io.edpn.backend.trade.adapter.persistence.SystemEliteIdRequestRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemEliteIdRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.CreateSystemEliteIdRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreateSystemEliteIdRequestPortTest {

    @Mock
    private MybatisSystemEliteIdRequestRepository mybatisSystemEliteIdRequestRepository;

    private CreateSystemEliteIdRequestPort underTest;

    @BeforeEach
    public void setup() {
        underTest = new SystemEliteIdRequestRepository(mybatisSystemEliteIdRequestRepository);
    }

    @Test
    public void testCreate() {
        String systemName = "someName";

        underTest.create(systemName);

        verify(mybatisSystemEliteIdRequestRepository).insert(systemName);
    }


}
