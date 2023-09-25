package io.edpn.backend.trade.adapter.persistence.systemeliteidrequest;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.adapter.persistence.SystemEliteIdRequestRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisSystemDataRequestEntity;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemEliteIdRequestRepository;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.SystemDataRequestEntityMapper;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.LoadAllSystemEliteIdRequestsPort;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoadAllSystemEliteIdRequestsPortTest {


    @Mock
    private MybatisSystemEliteIdRequestRepository mybatisSystemEliteIdRequestRepository;
    @Mock
    private SystemDataRequestEntityMapper systemDataRequestEntityMapper;

    private LoadAllSystemEliteIdRequestsPort underTest;

    @BeforeEach
    public void setup() {
        underTest = new SystemEliteIdRequestRepository(mybatisSystemEliteIdRequestRepository, systemDataRequestEntityMapper);
    }

    @Test
    public void testFindAll() {
        MybatisSystemDataRequestEntity entity1 = mock(MybatisSystemDataRequestEntity.class);
        MybatisSystemDataRequestEntity entity2 = mock(MybatisSystemDataRequestEntity.class);
        SystemDataRequest request1 = mock(SystemDataRequest.class);
        SystemDataRequest request2 = mock(SystemDataRequest.class);

        when(mybatisSystemEliteIdRequestRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(systemDataRequestEntityMapper.map(entity1)).thenReturn(request1);
        when(systemDataRequestEntityMapper.map(entity2)).thenReturn(request2);

        List<SystemDataRequest> result = underTest.loadAll();

        assertThat(result, hasSize(2));
        assertThat(result, containsInAnyOrder(request1, request2));
    }
}
