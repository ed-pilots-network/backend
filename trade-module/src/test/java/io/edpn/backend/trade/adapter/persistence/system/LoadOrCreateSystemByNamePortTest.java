package io.edpn.backend.trade.adapter.persistence.system;

import io.edpn.backend.trade.adapter.persistence.SystemRepository;
import io.edpn.backend.trade.adapter.persistence.entity.SystemEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.SystemEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.FindSystemFilterMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemRepository;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.port.outgoing.system.CreateOrLoadSystemPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoadOrCreateSystemByNamePortTest {

    @Mock
    private SystemEntityMapper systemEntityMapper;

    @Mock
    private FindSystemFilterMapper persistenceFindSystemFilterMapper;

    @Mock
    private MybatisSystemRepository mybatisSystemRepository;

    private CreateOrLoadSystemPort underTest;

    @BeforeEach
    public void setUp() {
        underTest = new SystemRepository(systemEntityMapper, persistenceFindSystemFilterMapper, mybatisSystemRepository);
    }

    @Test
    void createOrLoad() {
        System mockInputSystem = mock(System.class);
        SystemEntity mockInputSystemEntity = mock(SystemEntity.class);
        SystemEntity mockSavedSystemEntity = mock(SystemEntity.class);

        System expected = mock(System.class);

        when(systemEntityMapper.map(mockInputSystem)).thenReturn(mockInputSystemEntity);
        when(mybatisSystemRepository.createOrUpdateOnConflict(mockInputSystemEntity)).thenReturn(mockSavedSystemEntity);
        when(systemEntityMapper.map(mockSavedSystemEntity)).thenReturn(expected);

        System result = underTest.createOrLoad(mockInputSystem);

        assertThat(result, is(expected));
    }
}
