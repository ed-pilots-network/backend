package io.edpn.backend.exploration.adapter.persistence.system;

import io.edpn.backend.exploration.adapter.persistence.MybatisSystemRepository;
import io.edpn.backend.exploration.adapter.persistence.SystemRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemEntityMapper;
import io.edpn.backend.exploration.application.port.outgoing.system.SaveOrUpdateSystemPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaveOrUpdateSystemPortTest {
    @Mock
    private MybatisSystemRepository mybatisSystemRepository;

    @Mock
    private SystemEntityMapper<MybatisSystemEntity> systemEntityMapper;

    private SaveOrUpdateSystemPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemRepository(mybatisSystemRepository, systemEntityMapper);
    }

    @Test
    void save_shouldUpdateAndLoad() {

        System system = mock(System.class);
        MybatisSystemEntity entity = mock(MybatisSystemEntity.class);
        System loaded = mock(System.class);
        when(systemEntityMapper.map(system)).thenReturn(entity);
        when(mybatisSystemRepository.insertOrUpdateOnConflict(any())).thenReturn(entity);
        when(systemEntityMapper.map(entity)).thenReturn(loaded);


        System result = underTest.saveOrUpdate(system);


        assertThat(result, is(loaded));
    }
}