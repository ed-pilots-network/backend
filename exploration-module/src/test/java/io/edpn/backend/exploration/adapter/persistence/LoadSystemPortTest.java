package io.edpn.backend.exploration.adapter.persistence;

import io.edpn.backend.exploration.adapter.persistence.entity.SystemEntity;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.mapper.SystemEntityMapper;
import io.edpn.backend.exploration.application.port.outgoing.CreateSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemPort;
import io.edpn.backend.util.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoadSystemPortTest {
    @Mock
    private MybatisSystemRepository mybatisSystemRepository;

    @Mock
    private SystemEntityMapper systemEntityMapper;

    @Mock
    private IdGenerator idGenerator;

    private LoadSystemPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemRepository(mybatisSystemRepository, systemEntityMapper, idGenerator);
    }

    @Test
    void load_shouldFindByNameAndMap() {
        // Given
        String name = "system";
        SystemEntity entity = mock(SystemEntity.class);
        System mapped = mock(System.class);
        when(mybatisSystemRepository.findByName(name)).thenReturn(Optional.of(entity));
        when(systemEntityMapper.map(entity)).thenReturn(mapped);

        // When
        Optional<System> result = underTest.load(name);

        // Then
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(mapped));
    }
}