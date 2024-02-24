package io.edpn.backend.exploration.adapter.persistence.stationmaxlandingpadsizerequest;

import io.edpn.backend.exploration.adapter.persistence.MybatisStationMaxLandingPadSizeRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.StationMaxLandingPadSizeRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationMaxLandingPadSizeRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisStationMaxLandingPadSizeRequestEntityMapper;
import io.edpn.backend.exploration.application.domain.StationMaxLandingPadSizeRequest;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.LoadAllStationMaxLandingPadSizeRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoadAllStationMaxLandingPadSizeRequestPortTest {

    @Mock
    private MybatisStationMaxLandingPadSizeRequestRepository mybatisStationMaxLandingPadSizeRequestRepository;

    @Mock
    private MybatisStationMaxLandingPadSizeRequestEntityMapper mybatisStationMaxLandingPadSizeRequestEntityMapper;

    private LoadAllStationMaxLandingPadSizeRequestPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new StationMaxLandingPadSizeRequestRepository(mybatisStationMaxLandingPadSizeRequestRepository, mybatisStationMaxLandingPadSizeRequestEntityMapper);
    }

    @Test
    void load_shouldFindAllAndMap() {
        MybatisStationMaxLandingPadSizeRequestEntity mybatisStationMaxLandingPadSizeRequestEntity = mock(MybatisStationMaxLandingPadSizeRequestEntity.class);
        List<MybatisStationMaxLandingPadSizeRequestEntity> entities = List.of(mybatisStationMaxLandingPadSizeRequestEntity);
        StationMaxLandingPadSizeRequest mapped = mock(StationMaxLandingPadSizeRequest.class);
        when(mybatisStationMaxLandingPadSizeRequestRepository.findAll()).thenReturn(entities);
        when(mybatisStationMaxLandingPadSizeRequestEntityMapper.map(mybatisStationMaxLandingPadSizeRequestEntity)).thenReturn(mapped);


        List<StationMaxLandingPadSizeRequest> result = underTest.loadAll();


        assertThat(result, contains(mapped));
        verify(mybatisStationMaxLandingPadSizeRequestRepository).findAll();
    }

}