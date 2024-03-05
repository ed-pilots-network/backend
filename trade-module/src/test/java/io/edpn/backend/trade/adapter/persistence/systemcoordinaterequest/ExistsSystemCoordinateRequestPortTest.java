package io.edpn.backend.trade.adapter.persistence.systemcoordinaterequest;

import io.edpn.backend.trade.adapter.persistence.SystemCoordinateRequestRepository;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.SystemDataRequestEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemCoordinateRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.ExistsSystemCoordinateRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExistsSystemCoordinateRequestPortTest {

    @Mock
    private MybatisSystemCoordinateRequestRepository mybatisSystemCoordinateRequestRepository;
    @Mock
    private SystemDataRequestEntityMapper systemDataRequestEntityMapper;

    private ExistsSystemCoordinateRequestPort underTest;

    @BeforeEach
    public void setup() {
        underTest = new SystemCoordinateRequestRepository(mybatisSystemCoordinateRequestRepository, systemDataRequestEntityMapper);
    }

    @Test
    public void testExistsWhenSystemNameExists() {
        String systemName = "someName";

        when(mybatisSystemCoordinateRequestRepository.exists(systemName)).thenReturn(true);

        boolean result = underTest.exists(systemName);

        assertThat(result, is(true));
    }

    @Test
    public void testExistsWhenSystemNameDoesNotExist() {
        String systemName = "someName";

        when(mybatisSystemCoordinateRequestRepository.exists(systemName)).thenReturn(false);

        boolean result = underTest.exists(systemName);

        assertThat(result, is(false));
    }

}
