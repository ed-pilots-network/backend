package io.edpn.backend.trade.adapter.persistence.stationlandingpadsizerequest;

import io.edpn.backend.trade.adapter.persistence.StationLandingPadSizeRequestRepository;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisStationDataRequestEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationLandingPadSizeRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.CreateIfNotExistsStationLandingPadSizeRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreateIfNotExistsStationLandingPadSizeRequestPortTest {

    @Mock
    private MybatisStationLandingPadSizeRequestRepository mybatisStationLandingPadSizeRequestRepository;
    @Mock
    private MybatisStationDataRequestEntityMapper mybatisStationDataRequestEntityMapper;

    private CreateIfNotExistsStationLandingPadSizeRequestPort underTest;

    @BeforeEach
    public void setup() {
        underTest = new StationLandingPadSizeRequestRepository(mybatisStationLandingPadSizeRequestRepository, mybatisStationDataRequestEntityMapper);
    }

    @Test
    public void testCreate() {
        String systemName = "someName";
        String stationName = "someName2";

        underTest.createIfNotExists(systemName, stationName);

        verify(mybatisStationLandingPadSizeRequestRepository).insertIfNotExists(systemName, stationName);
    }


}
