package io.edpn.backend.trade.adapter.persistence.stationrequireodysseyrequest;

import io.edpn.backend.trade.adapter.persistence.StationLandingPadSizeRequestRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationLandingPadSizeRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.DeleteStationLandingPadSizeRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeleteStationLandingPadSizeRequestPortTest {

    @Mock
    private MybatisStationLandingPadSizeRequestRepository mybatisStationLandingPadSizeRequestRepository;

    private DeleteStationLandingPadSizeRequestPort underTest;

    @BeforeEach
    public void setup() {
        underTest = new StationLandingPadSizeRequestRepository(mybatisStationLandingPadSizeRequestRepository);
    }

    @Test
    public void testCreate() {
        String systemName = "someName";
        String stationName = "someName2";

        underTest.delete(systemName, stationName);

        verify(mybatisStationLandingPadSizeRequestRepository).delete(systemName, stationName);
    }


}
