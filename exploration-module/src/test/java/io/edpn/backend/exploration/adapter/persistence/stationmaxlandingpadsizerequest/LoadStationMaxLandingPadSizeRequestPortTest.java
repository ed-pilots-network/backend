package io.edpn.backend.exploration.adapter.persistence.stationmaxlandingpadsizerequest;

import io.edpn.backend.exploration.adapter.persistence.MybatisStationMaxLandingPadSizeRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.StationMaxLandingPadSizeRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationMaxLandingPadSizeRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisStationMaxLandingPadSizeRequestEntityMapper;
import io.edpn.backend.exploration.application.domain.StationMaxLandingPadSizeRequest;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.LoadStationMaxLandingPadSizeRequestPort;
import io.edpn.backend.util.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoadStationMaxLandingPadSizeRequestPortTest {

    @Mock
    private MybatisStationMaxLandingPadSizeRequestRepository mybatisStationMaxLandingPadSizeRequestRepository;

    @Mock
    private MybatisStationMaxLandingPadSizeRequestEntityMapper mybatisStationMaxLandingPadSizeRequestEntityMapper;

    private LoadStationMaxLandingPadSizeRequestPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new StationMaxLandingPadSizeRequestRepository(mybatisStationMaxLandingPadSizeRequestRepository, mybatisStationMaxLandingPadSizeRequestEntityMapper);
    }


    @Test
    void create_shouldMapAndInsert() {
        StationMaxLandingPadSizeRequest request = mock(StationMaxLandingPadSizeRequest.class);
        when(request.systemName()).thenReturn("system");
        when(request.stationName()).thenReturn("station");
        Module module = Module.TRADE;
        when(request.requestingModule()).thenReturn(module);
        MybatisStationMaxLandingPadSizeRequestEntity entity = mock(MybatisStationMaxLandingPadSizeRequestEntity.class);
        when(mybatisStationMaxLandingPadSizeRequestRepository.find(module, "system", "station")).thenReturn(Optional.of(entity));
        when(mybatisStationMaxLandingPadSizeRequestEntityMapper.map(entity)).thenReturn(request);

        Optional<StationMaxLandingPadSizeRequest> result = underTest.load(request);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(request));

        verify(mybatisStationMaxLandingPadSizeRequestRepository).find(module, "system", "station");
    }
}
