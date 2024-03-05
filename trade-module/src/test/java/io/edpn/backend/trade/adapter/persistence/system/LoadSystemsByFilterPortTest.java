package io.edpn.backend.trade.adapter.persistence.system;

import io.edpn.backend.trade.adapter.persistence.SystemRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisSystemEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.MybatisFindSystemFilter;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisFindSystemFilterMapper;
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
    private MybatisSystemEntityMapper mybatisSystemEntityMapper;

    @Mock
    private MybatisFindSystemFilterMapper persistenceMybatisFindSystemFilterMapper;

    @Mock
    private MybatisSystemRepository mybatisSystemRepository;

    private LoadSystemsByFilterPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemRepository(mybatisSystemEntityMapper, persistenceMybatisFindSystemFilterMapper, mybatisSystemRepository);
    }

    @Test
    void testFindByFilter() {
        io.edpn.backend.trade.application.domain.filter.FindSystemFilter findSystemFilter = mock(io.edpn.backend.trade.application.domain.filter.FindSystemFilter.class);
        MybatisFindSystemFilter mybatisFindSystemFilter = mock(MybatisFindSystemFilter.class);
        MybatisSystemEntity mybatisSystemEntity = mock(MybatisSystemEntity.class);
        System system = mock(System.class);

        when(persistenceMybatisFindSystemFilterMapper.map(findSystemFilter)).thenReturn(mybatisFindSystemFilter);
        when(mybatisSystemRepository.findByFilter(mybatisFindSystemFilter)).thenReturn(List.of(mybatisSystemEntity));
        when(mybatisSystemEntityMapper.map(mybatisSystemEntity)).thenReturn(system);

        List<System> result = underTest.loadByFilter(findSystemFilter);

        assertThat(result, Matchers.containsInAnyOrder(system));
    }
}
