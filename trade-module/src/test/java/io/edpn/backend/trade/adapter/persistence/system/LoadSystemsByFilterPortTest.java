package io.edpn.backend.trade.adapter.persistence.system;

import io.edpn.backend.trade.adapter.persistence.SystemRepository;
import io.edpn.backend.trade.adapter.persistence.entity.SystemEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.SystemEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.FindSystemFilter;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.FindSystemFilterMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemRepository;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemsByFilterPort;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoadSystemsByFilterPortTest {

    @Mock
    private SystemEntityMapper systemEntityMapper;

    @Mock
    private FindSystemFilterMapper persistenceFindSystemFilterMapper;

    @Mock
    private MybatisSystemRepository mybatisSystemRepository;

    private LoadSystemsByFilterPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemRepository(systemEntityMapper, persistenceFindSystemFilterMapper, mybatisSystemRepository);
    }

    @Test
    void testFindByFilter() {
        io.edpn.backend.trade.application.domain.filter.FindSystemFilter findSystemFilter = mock(io.edpn.backend.trade.application.domain.filter.FindSystemFilter.class);
        FindSystemFilter persistenceFindSystemFilter = mock(FindSystemFilter.class);
        SystemEntity systemEntity = mock(SystemEntity.class);
        System system = mock(System.class);

        when(persistenceFindSystemFilterMapper.map(findSystemFilter)).thenReturn(persistenceFindSystemFilter);
        when(mybatisSystemRepository.findByFilter(persistenceFindSystemFilter)).thenReturn(List.of(systemEntity));
        when(systemEntityMapper.map(systemEntity)).thenReturn(system);

        List<System> result = underTest.loadByFilter(findSystemFilter);

        assertThat(result, Matchers.containsInAnyOrder(system));
    }
}
