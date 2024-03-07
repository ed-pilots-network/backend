package io.edpn.backend.trade.adapter.persistence.stationlandingpadsizerequest;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.adapter.persistence.StationLandingPadSizeRequestRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisStationDataRequestEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisStationDataRequestEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationLandingPadSizeRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest.LoadAllStationLandingPadSizeRequestsPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoadAllStationLandingPadSizeRequestsPortTest {

    @Mock
    private MybatisStationLandingPadSizeRequestRepository mybatisStationLandingPadSizeRequestRepository;
    @Mock
    private MybatisStationDataRequestEntityMapper mybatisStationDataRequestEntityMapper;

    private LoadAllStationLandingPadSizeRequestsPort underTest;

    @BeforeEach
    public void setup() {
        underTest = new StationLandingPadSizeRequestRepository(mybatisStationLandingPadSizeRequestRepository, mybatisStationDataRequestEntityMapper);
    }

    @Test
    public void testFindAll() {
        MybatisStationDataRequestEntity entity1 = mock(MybatisStationDataRequestEntity.class);
        MybatisStationDataRequestEntity entity2 = mock(MybatisStationDataRequestEntity.class);
        StationDataRequest request1 = mock(StationDataRequest.class);
        StationDataRequest request2 = mock(StationDataRequest.class);

        when(mybatisStationLandingPadSizeRequestRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(mybatisStationDataRequestEntityMapper.map(entity1)).thenReturn(request1);
        when(mybatisStationDataRequestEntityMapper.map(entity2)).thenReturn(request2);

        List<StationDataRequest> result = underTest.loadAll();

        assertThat(result, hasSize(2));
        assertThat(result, containsInAnyOrder(request1, request2));
    }
}
