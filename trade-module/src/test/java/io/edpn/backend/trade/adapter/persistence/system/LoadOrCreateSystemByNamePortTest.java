package io.edpn.backend.trade.adapter.persistence.system;

import io.edpn.backend.trade.adapter.persistence.SystemRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisSystemEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisFindSystemFilterMapper;
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
    private MybatisSystemEntityMapper mybatisSystemEntityMapper;

    @Mock
    private MybatisFindSystemFilterMapper persistenceMybatisFindSystemFilterMapper;

    @Mock
    private MybatisSystemRepository mybatisSystemRepository;

    private CreateOrLoadSystemPort underTest;

    @BeforeEach
    public void setUp() {
        underTest = new SystemRepository(mybatisSystemEntityMapper, persistenceMybatisFindSystemFilterMapper, mybatisSystemRepository);
    }

    @Test
    void createOrLoad() {
        System mockInputSystem = mock(System.class);
        MybatisSystemEntity mockInputMybatisSystemEntity = mock(MybatisSystemEntity.class);
        MybatisSystemEntity mockSavedMybatisSystemEntity = mock(MybatisSystemEntity.class);

        System expected = mock(System.class);

        when(mybatisSystemEntityMapper.map(mockInputSystem)).thenReturn(mockInputMybatisSystemEntity);
        when(mybatisSystemRepository.createOrUpdateOnConflict(mockInputMybatisSystemEntity)).thenReturn(mockSavedMybatisSystemEntity);
        when(mybatisSystemEntityMapper.map(mockSavedMybatisSystemEntity)).thenReturn(expected);

        System result = underTest.createOrLoad(mockInputSystem);

        assertThat(result, is(expected));
    }
}
