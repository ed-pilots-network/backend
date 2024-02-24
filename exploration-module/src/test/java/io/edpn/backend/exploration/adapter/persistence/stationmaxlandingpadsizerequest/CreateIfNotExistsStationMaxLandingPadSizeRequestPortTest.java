package io.edpn.backend.exploration.adapter.persistence.stationmaxlandingpadsizerequest;

import io.edpn.backend.exploration.adapter.persistence.MybatisStationMaxLandingPadSizeRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.StationMaxLandingPadSizeRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationMaxLandingPadSizeRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisStationMaxLandingPadSizeRequestEntityMapper;
import io.edpn.backend.exploration.application.domain.StationMaxLandingPadSizeRequest;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.CreateIfNotExistsStationMaxLandingPadSizeRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateIfNotExistsStationMaxLandingPadSizeRequestPortTest {

    @Mock
    private MybatisStationMaxLandingPadSizeRequestRepository mybatisStationMaxLandingPadSizeRequestRepository;

    @Mock
    private MybatisStationMaxLandingPadSizeRequestEntityMapper mybatisStationMaxLandingPadSizeRequestEntityMapper;

    private CreateIfNotExistsStationMaxLandingPadSizeRequestPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new StationMaxLandingPadSizeRequestRepository(mybatisStationMaxLandingPadSizeRequestRepository, mybatisStationMaxLandingPadSizeRequestEntityMapper);
    }

    @Test
    void create_shouldMapAndInsert() {
        StationMaxLandingPadSizeRequest request = mock(StationMaxLandingPadSizeRequest.class);
        MybatisStationMaxLandingPadSizeRequestEntity entity = mock(MybatisStationMaxLandingPadSizeRequestEntity.class);
        when(mybatisStationMaxLandingPadSizeRequestEntityMapper.map(request)).thenReturn(entity);


        underTest.createIfNotExists(request);


        verify(mybatisStationMaxLandingPadSizeRequestRepository).insertIfNotExists(entity);
    }
}