package io.edpn.backend.trade.adapter.persistence.stationrequireodysseyrequest;

import io.edpn.backend.trade.adapter.persistence.StationPlanetaryRequestRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationPlanetaryRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.CreateStationPlanetaryRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreateStationPlanetaryRequestPortTest {

    @Mock
    private MybatisStationPlanetaryRequestRepository mybatisStationPlanetaryRequestRepository;

    private CreateStationPlanetaryRequestPort underTest;

    @BeforeEach
    public void setup() {
        underTest = new StationPlanetaryRequestRepository(mybatisStationPlanetaryRequestRepository);
    }

    @Test
    public void testCreate() {
        String systemName = "someName";
        String stationName = "someName2";

        underTest.create(systemName, stationName);

        verify(mybatisStationPlanetaryRequestRepository).insert(systemName, stationName);
    }


}
