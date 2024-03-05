package io.edpn.backend.exploration.adapter.persistence.stationarrivaldistancerequest;

import io.edpn.backend.exploration.adapter.persistence.MybatisStationArrivalDistanceRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.StationArrivalDistanceRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationArrivalDistanceRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisStationArrivalDistanceRequestEntityMapper;
import io.edpn.backend.exploration.application.domain.StationArrivalDistanceRequest;
import io.edpn.backend.exploration.application.port.outgoing.stationarrivaldistancerequest.LoadStationArrivalDistanceRequestPort;
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
public class LoadStationArrivalDistanceRequestPortTest {

    @Mock
    private MybatisStationArrivalDistanceRequestRepository mybatisStationArrivalDistanceRequestRepository;

    @Mock
    private MybatisStationArrivalDistanceRequestEntityMapper mybatisStationArrivalDistanceRequestEntityMapper;

    private LoadStationArrivalDistanceRequestPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new StationArrivalDistanceRequestRepository(mybatisStationArrivalDistanceRequestRepository, mybatisStationArrivalDistanceRequestEntityMapper);
    }


    @Test
    void create_shouldMapAndInsert() {
        StationArrivalDistanceRequest request = mock(StationArrivalDistanceRequest.class);
        when(request.systemName()).thenReturn("system");
        when(request.stationName()).thenReturn("station");
        Module module = Module.TRADE;
        when(request.requestingModule()).thenReturn(module);
        MybatisStationArrivalDistanceRequestEntity entity = mock(MybatisStationArrivalDistanceRequestEntity.class);
        when(mybatisStationArrivalDistanceRequestRepository.find(module, "system", "station")).thenReturn(Optional.of(entity));
        when(mybatisStationArrivalDistanceRequestEntityMapper.map(entity)).thenReturn(request);

        Optional<StationArrivalDistanceRequest> result = underTest.load(request);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(request));

        verify(mybatisStationArrivalDistanceRequestRepository).find(module, "system", "station");
    }
}
