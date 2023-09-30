package io.edpn.backend.trade.adapter.persistence.stationrequireodysseyrequest;

import io.edpn.backend.trade.adapter.persistence.StationRequireOdysseyRequestRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationRequireOdysseyRequestRepository;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.StationDataRequestEntityMapper;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.ExistsStationRequireOdysseyRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExistsStationRequireOdysseyRequestPortTest {

    @Mock
    private MybatisStationRequireOdysseyRequestRepository mybatisStationRequireOdysseyRequestRepository;
    @Mock
    private StationDataRequestEntityMapper stationDataRequestEntityMapper;

    private ExistsStationRequireOdysseyRequestPort underTest;

    @BeforeEach
    public void setup() {
        underTest = new StationRequireOdysseyRequestRepository(mybatisStationRequireOdysseyRequestRepository, stationDataRequestEntityMapper);
    }

    @Test
    public void testExistsWhenSystemNameExists() {
        String systemName = "someName";
        String stationName = "someName2";

        when(mybatisStationRequireOdysseyRequestRepository.exists(systemName, stationName)).thenReturn(true);

        boolean result = underTest.exists(systemName, stationName);

        assertThat(result, is(true));
    }

    @Test
    public void testExistsWhenSystemNameDoesNotExist() {
        String systemName = "someName";
        String stationName = "someName2";

        when(mybatisStationRequireOdysseyRequestRepository.exists(systemName, stationName)).thenReturn(false);

        boolean result = underTest.exists(systemName, stationName);

        assertThat(result, is(false));
    }

}
