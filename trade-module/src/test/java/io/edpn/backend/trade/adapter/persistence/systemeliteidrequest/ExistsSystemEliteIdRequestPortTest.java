package io.edpn.backend.trade.adapter.persistence.systemeliteidrequest;

import io.edpn.backend.trade.adapter.persistence.SystemEliteIdRequestRepository;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisSystemDataRequestEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemEliteIdRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.ExistsSystemEliteIdRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExistsSystemEliteIdRequestPortTest {

    @Mock
    private MybatisSystemEliteIdRequestRepository mybatisSystemEliteIdRequestRepository;
    @Mock
    private MybatisSystemDataRequestEntityMapper mybatisSystemDataRequestEntityMapper;

    private ExistsSystemEliteIdRequestPort underTest;

    @BeforeEach
    public void setup() {
        underTest = new SystemEliteIdRequestRepository(mybatisSystemEliteIdRequestRepository, mybatisSystemDataRequestEntityMapper);
    }

    @Test
    public void testExistsWhenSystemNameExists() {
        String systemName = "someName";

        when(mybatisSystemEliteIdRequestRepository.exists(systemName)).thenReturn(true);

        boolean result = underTest.exists(systemName);

        assertThat(result, is(true));
    }

    @Test
    public void testExistsWhenSystemNameDoesNotExist() {
        String systemName = "someName";

        when(mybatisSystemEliteIdRequestRepository.exists(systemName)).thenReturn(false);

        boolean result = underTest.exists(systemName);

        assertThat(result, is(false));
    }

}
