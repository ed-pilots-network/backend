package io.edpn.backend.trade.adapter.persistence.stationlandingpadsizerequest;

import io.edpn.backend.trade.adapter.persistence.StationLandingPadSizeRequestRepository;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.StationDataRequestEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationLandingPadSizeRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.CreateStationLandingPadSizeRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreateStationLandingPadSizeRequestPortTest {

    @Mock
    private MybatisStationLandingPadSizeRequestRepository mybatisStationLandingPadSizeRequestRepository;
    @Mock
    private StationDataRequestEntityMapper stationDataRequestEntityMapper;

    private CreateStationLandingPadSizeRequestPort underTest;

    @BeforeEach
    public void setup() {
        underTest = new StationLandingPadSizeRequestRepository(mybatisStationLandingPadSizeRequestRepository, stationDataRequestEntityMapper);
    }

    @Test
    public void testCreate() {
        String systemName = "someName";
        String stationName = "someName2";

        underTest.create(systemName, stationName);

        verify(mybatisStationLandingPadSizeRequestRepository).insert(systemName, stationName);
    }


}
