package io.edpn.backend.exploration.adapter.persistence;

import io.edpn.backend.exploration.adapter.persistence.entity.SystemEntity;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.mapper.SystemEntityMapper;
import io.edpn.backend.exploration.application.port.outgoing.SaveSystemPort;
import io.edpn.backend.util.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaveSystemPortTest {
    @Mock
    private MybatisSystemRepository mybatisSystemRepository;

    @Mock
    private SystemEntityMapper systemEntityMapper;

    @Mock
    private IdGenerator idGenerator;

    private SaveSystemPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemRepository(mybatisSystemRepository, systemEntityMapper, idGenerator);
    }

    @Test
    void save_shouldUpdateAndLoad() {

        System system = mock(System.class);
        SystemEntity entity = mock(SystemEntity.class);
        System loaded = mock(System.class);
        when(systemEntityMapper.map(system)).thenReturn(entity);
        when(mybatisSystemRepository.findById(any())).thenReturn(Optional.of(entity));
        when(systemEntityMapper.map(entity)).thenReturn(loaded);


        System result = underTest.save(system);


        assertThat(result, is(loaded));
        verify(mybatisSystemRepository).update(entity);
        verify(mybatisSystemRepository).findById(system.id());
    }
}