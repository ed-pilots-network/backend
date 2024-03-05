package io.edpn.backend.trade.adapter.persistence.stationarrivaldistancerequest;

import io.edpn.backend.trade.adapter.persistence.StationArrivalDistanceRequestRepository;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisStationDataRequestEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationArrivalDistanceRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.ExistsStationArrivalDistanceRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExistsStationArrivalDistanceRequestPortTest {

    @Mock
    private MybatisStationArrivalDistanceRequestRepository mybatisStationArrivalDistanceRequestRepository;
    @Mock
    private MybatisStationDataRequestEntityMapper mybatisStationDataRequestEntityMapper;

    private ExistsStationArrivalDistanceRequestPort underTest;

    @BeforeEach
    public void setup() {
        underTest = new StationArrivalDistanceRequestRepository(mybatisStationArrivalDistanceRequestRepository, mybatisStationDataRequestEntityMapper);
    }

    @Test
    public void testExistsWhenSystemNameExists() {
        String systemName = "someName";
        String stationName = "someName2";

        when(mybatisStationArrivalDistanceRequestRepository.exists(systemName, stationName)).thenReturn(true);

        boolean result = underTest.exists(systemName, stationName);

        assertThat(result, is(true));
    }

    @Test
    public void testExistsWhenSystemNameDoesNotExist() {
        String systemName = "someName";
        String stationName = "someName2";

        when(mybatisStationArrivalDistanceRequestRepository.exists(systemName, stationName)).thenReturn(false);

        boolean result = underTest.exists(systemName, stationName);

        assertThat(result, is(false));
    }

}
