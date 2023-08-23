package io.edpn.backend.exploration.adapter.persistence.system;

import io.edpn.backend.exploration.adapter.persistence.MybatisSystemRepository;
import io.edpn.backend.exploration.adapter.persistence.SystemRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.mapper.SystemEntityMapper;
import io.edpn.backend.exploration.application.port.outgoing.system.CreateSystemPort;
import io.edpn.backend.util.IdGenerator;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateSystemPortTest {
    @Mock
    private MybatisSystemRepository mybatisSystemRepository;

    @Mock
    private SystemEntityMapper<MybatisSystemEntity> systemEntityMapper;

    @Mock
    private IdGenerator idGenerator;

    private CreateSystemPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemRepository(mybatisSystemRepository, systemEntityMapper, idGenerator);
    }

    @Test
    void create_shouldInsertAndLoad() {

        String systemName = "system";
        UUID id = UUID.randomUUID();
        when(idGenerator.generateId()).thenReturn(id);
        System loaded = mock(System.class);
        MybatisSystemEntity mybatisSystemEntity = mock(MybatisSystemEntity.class);
        when(mybatisSystemRepository.findByName(systemName)).thenReturn(Optional.of(mybatisSystemEntity));
        when(systemEntityMapper.map(mybatisSystemEntity)).thenReturn(loaded);


        System result = underTest.create(systemName);


        assertThat(result, is(loaded));
        verify(mybatisSystemRepository).insert(new MybatisSystemEntity(id, systemName, null, null, null, null, null));
        verify(mybatisSystemRepository).findByName(systemName);
    }

    @Test
    void create_shouldThrow_whenLoadFails() {

        String systemName = "system";
        UUID id = UUID.randomUUID();
        when(idGenerator.generateId()).thenReturn(id);
        when(mybatisSystemRepository.findByName(systemName)).thenReturn(Optional.empty());


        DatabaseEntityNotFoundException exception = assertThrows(DatabaseEntityNotFoundException.class, () -> underTest.create(systemName));


        assertThat(exception.getMessage(), is("System with name '" + systemName + "' could not be found after create"));
    }
}