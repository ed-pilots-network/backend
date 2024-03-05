package io.edpn.backend.trade.adapter.persistence.systemcoordinaterequest;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.adapter.persistence.SystemCoordinateRequestRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisSystemDataRequestEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisSystemDataRequestEntityMapper;
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
    private MybatisSystemDataRequestEntityMapper mybatisSystemDataRequestEntityMapper;

    private LoadAllSystemCoordinateRequestsPort underTest;

    @BeforeEach
    public void setup() {
        underTest = new SystemCoordinateRequestRepository(mybatisSystemCoordinateRequestRepository, mybatisSystemDataRequestEntityMapper);
    }

    @Test
    public void testFindAll() {
        MybatisSystemDataRequestEntity entity1 = mock(MybatisSystemDataRequestEntity.class);
        MybatisSystemDataRequestEntity entity2 = mock(MybatisSystemDataRequestEntity.class);
        SystemDataRequest request1 = mock(SystemDataRequest.class);
        SystemDataRequest request2 = mock(SystemDataRequest.class);

        when(mybatisSystemCoordinateRequestRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(mybatisSystemDataRequestEntityMapper.map(entity1)).thenReturn(request1);
        when(mybatisSystemDataRequestEntityMapper.map(entity2)).thenReturn(request2);

        List<SystemDataRequest> result = underTest.loadAll();

        assertThat(result, hasSize(2));
        assertThat(result, containsInAnyOrder(request1, request2));
    }
}
