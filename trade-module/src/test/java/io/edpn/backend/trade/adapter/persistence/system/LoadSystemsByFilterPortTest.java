package io.edpn.backend.trade.adapter.persistence.system;

import io.edpn.backend.trade.adapter.persistence.SystemRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemRepository;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.domain.filter.FindSystemFilter;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.SystemEntityMapper;
import io.edpn.backend.trade.application.dto.persistence.filter.PersistenceFindSystemFilter;
import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistenceFindSystemFilterMapper;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemsByFilterPort;
import io.edpn.backend.util.IdGenerator;
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
    private IdGenerator idGenerator;

    @Mock
    private SystemEntityMapper<MybatisSystemEntity> mybatisSystemEntityMapper;

    @Mock
    private PersistenceFindSystemFilterMapper persistenceFindSystemFilterMapper;

    @Mock
    private MybatisSystemRepository mybatisSystemRepository;

    private LoadSystemsByFilterPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemRepository(idGenerator, mybatisSystemEntityMapper, persistenceFindSystemFilterMapper, mybatisSystemRepository);
    }

    @Test
    void testFindByFilter() {
        FindSystemFilter findSystemFilter = mock(FindSystemFilter.class);
        PersistenceFindSystemFilter persistenceFindSystemFilter = mock(PersistenceFindSystemFilter.class);
        MybatisSystemEntity systemEntity = mock(MybatisSystemEntity.class);
        System system = mock(System.class);

        when(persistenceFindSystemFilterMapper.map(findSystemFilter)).thenReturn(persistenceFindSystemFilter);
        when(mybatisSystemRepository.findByFilter(persistenceFindSystemFilter)).thenReturn(List.of(systemEntity));
        when(mybatisSystemEntityMapper.map(systemEntity)).thenReturn(system);

        List<System> result = underTest.loadByFilter(findSystemFilter);

        assertThat(result, Matchers.containsInAnyOrder(system));
    }
}
