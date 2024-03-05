package io.edpn.backend.trade.adapter.persistence.systemcoordinaterequest;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.adapter.persistence.SystemCoordinateRequestRepository;
import io.edpn.backend.trade.adapter.persistence.entity.SystemDataRequestEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.SystemDataRequestEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemCoordinateRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.LoadAllSystemCoordinateRequestsPort;
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
public class LoadAllSystemCoordinateRequestsPortTest {

    @Mock
    private MybatisSystemCoordinateRequestRepository mybatisSystemCoordinateRequestRepository;
    @Mock
    private SystemDataRequestEntityMapper systemDataRequestEntityMapper;

    private LoadAllSystemCoordinateRequestsPort underTest;

    @BeforeEach
    public void setup() {
        underTest = new SystemCoordinateRequestRepository(mybatisSystemCoordinateRequestRepository, systemDataRequestEntityMapper);
    }

    @Test
    public void testFindAll() {
        SystemDataRequestEntity entity1 = mock(SystemDataRequestEntity.class);
        SystemDataRequestEntity entity2 = mock(SystemDataRequestEntity.class);
        SystemDataRequest request1 = mock(SystemDataRequest.class);
        SystemDataRequest request2 = mock(SystemDataRequest.class);

        when(mybatisSystemCoordinateRequestRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(systemDataRequestEntityMapper.map(entity1)).thenReturn(request1);
        when(systemDataRequestEntityMapper.map(entity2)).thenReturn(request2);

        List<SystemDataRequest> result = underTest.loadAll();

        assertThat(result, hasSize(2));
        assertThat(result, containsInAnyOrder(request1, request2));
    }
}
