package io.edpn.backend.trade.adapter.persistence.stationarrivaldistancerequest;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.adapter.persistence.StationArrivalDistanceRequestRepository;
import io.edpn.backend.trade.adapter.persistence.entity.StationDataRequestEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.StationDataRequestEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationArrivalDistanceRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.LoadAllStationArrivalDistanceRequestsPort;
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
public class LoadAllStationArrivalDistanceRequestsPortTest {


    @Mock
    private MybatisStationArrivalDistanceRequestRepository mybatisStationArrivalDistanceRequestRepository;
    @Mock
    private StationDataRequestEntityMapper stationDataRequestEntityMapper;

    private LoadAllStationArrivalDistanceRequestsPort underTest;

    @BeforeEach
    public void setup() {
        underTest = new StationArrivalDistanceRequestRepository(mybatisStationArrivalDistanceRequestRepository, stationDataRequestEntityMapper);
    }

    @Test
    public void testFindAll() {
        StationDataRequestEntity entity1 = mock(StationDataRequestEntity.class);
        StationDataRequestEntity entity2 = mock(StationDataRequestEntity.class);
        StationDataRequest request1 = mock(StationDataRequest.class);
        StationDataRequest request2 = mock(StationDataRequest.class);

        when(mybatisStationArrivalDistanceRequestRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(stationDataRequestEntityMapper.map(entity1)).thenReturn(request1);
        when(stationDataRequestEntityMapper.map(entity2)).thenReturn(request2);

        List<StationDataRequest> result = underTest.loadAll();

        assertThat(result, hasSize(2));
        assertThat(result, containsInAnyOrder(request1, request2));
    }
}
