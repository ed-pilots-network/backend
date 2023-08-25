package io.edpn.backend.trade.adapter.persistence.stationplanetaryrequest;

import io.edpn.backend.trade.adapter.persistence.StationPlanetaryRequestRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationPlanetaryRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest.DeleteStationPlanetaryRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeleteStationPlanetaryRequestPortTest {

    @Mock
    private MybatisStationPlanetaryRequestRepository mybatisStationPlanetaryRequestRepository;

    private DeleteStationPlanetaryRequestPort underTest;

    @BeforeEach
    public void setup() {
        underTest = new StationPlanetaryRequestRepository(mybatisStationPlanetaryRequestRepository);
    }

    @Test
    public void testCreate() {
        String systemName = "someName";
        String stationName = "someName2";

        underTest.delete(systemName, stationName);

        verify(mybatisStationPlanetaryRequestRepository).delete(systemName, stationName);
    }


}
