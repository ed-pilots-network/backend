package io.edpn.backend.exploration.adapter.persistence.systemeliteidrequest;

import io.edpn.backend.exploration.adapter.persistence.MybatisSystemEliteIdRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.SystemEliteIdRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEliteIdRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisSystemEliteIdRequestEntityMapper;
import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.LoadAllSystemEliteIdRequestPort;
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
class LoadAllSystemEliteIdRequestPortTest {

    @Mock
    private MybatisSystemEliteIdRequestRepository mybatisSystemEliteIdRequestRepository;

    @Mock
    private MybatisSystemEliteIdRequestEntityMapper mybatisSystemEliteIdRequestEntityMapper;

    private LoadAllSystemEliteIdRequestPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemEliteIdRequestRepository(mybatisSystemEliteIdRequestRepository, mybatisSystemEliteIdRequestEntityMapper);
    }

    @Test
    void load_shouldFindAllAndMap() {

        MybatisSystemEliteIdRequestEntity mybatisSystemEliteIdRequestEntity = mock(MybatisSystemEliteIdRequestEntity.class);
        List<MybatisSystemEliteIdRequestEntity> entities = List.of(mybatisSystemEliteIdRequestEntity);
        SystemEliteIdRequest mapped = mock(SystemEliteIdRequest.class);
        when(mybatisSystemEliteIdRequestRepository.findAll()).thenReturn(entities);
        when(mybatisSystemEliteIdRequestEntityMapper.map(mybatisSystemEliteIdRequestEntity)).thenReturn(mapped);


        List<SystemEliteIdRequest> result = underTest.loadAll();


        assertThat(result, contains(mapped));
        verify(mybatisSystemEliteIdRequestRepository).findAll();
    }

}