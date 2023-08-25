package io.edpn.backend.trade.adapter.persistence.stationplanetaryrequest;

import io.edpn.backend.trade.adapter.persistence.StationPlanetaryRequestRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationPlanetaryRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.ExistsStationPlanetaryRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExistsStationPlanetaryRequestPortTest {

    @Mock
    private MybatisStationPlanetaryRequestRepository mybatisStationPlanetaryRequestRepository;

    private ExistsStationPlanetaryRequestPort underTest;

    @BeforeEach
    public void setup() {
        underTest = new StationPlanetaryRequestRepository(mybatisStationPlanetaryRequestRepository);
    }

    @Test
    public void testExistsWhenSystemNameExists() {
        String systemName = "someName";
        String stationName = "someName2";

        when(mybatisStationPlanetaryRequestRepository.exists(systemName, stationName)).thenReturn(true);

        boolean result = underTest.exists(systemName, stationName);

        assertThat(result, is(true));
    }

    @Test
    public void testExistsWhenSystemNameDoesNotExist() {
        String systemName = "someName";
        String stationName = "someName2";

        when(mybatisStationPlanetaryRequestRepository.exists(systemName, stationName)).thenReturn(false);

        boolean result = underTest.exists(systemName, stationName);

        assertThat(result, is(false));
    }

}
