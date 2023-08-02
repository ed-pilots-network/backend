package io.edpn.backend.exploration.adapter.persistence;

import io.edpn.backend.exploration.adapter.persistence.entity.SystemEntity;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.mapper.SystemEntityMapper;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemsByNameContainingPort;
import io.edpn.backend.util.IdGenerator;
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
class LoadSystemsByNameContainingPortTest {
    @Mock
    private MybatisSystemRepository mybatisSystemRepository;

    @Mock
    private SystemEntityMapper systemEntityMapper;

    @Mock
    private IdGenerator idGenerator;

    private LoadSystemsByNameContainingPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemRepository(mybatisSystemRepository, systemEntityMapper, idGenerator);
    }

    @Test
    void load_withNameAndAmount_shouldFindFromSearchbarAndMap() {
        // Given
        String name = "system";
        int amount = 10;
        SystemEntity systemEntity = mock(SystemEntity.class);
        List<SystemEntity> entities = List.of(systemEntity);
        System mapped = mock(System.class);
        when(mybatisSystemRepository.findFromSearchbar(name, amount)).thenReturn(entities);
        when(systemEntityMapper.map(systemEntity)).thenReturn(mapped);

        // When
        List<System> result = underTest.load(name, amount);

        // Then
        assertThat(result, contains(mapped));
        verify(mybatisSystemRepository).findFromSearchbar(name, amount);
    }
}