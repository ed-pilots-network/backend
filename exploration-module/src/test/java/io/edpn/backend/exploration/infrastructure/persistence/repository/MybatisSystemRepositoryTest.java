package io.edpn.backend.exploration.infrastructure.persistence.repository;

import io.edpn.backend.exploration.domain.model.System;
import io.edpn.backend.exploration.domain.repository.SystemRepository;
import io.edpn.backend.exploration.infrastructure.persistence.entity.SystemEntity;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.entity.SystemMapper;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.mybatis.SystemEntityMapper;
import io.edpn.backend.util.IdGenerator;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MybatisSystemRepositoryTest {
    @Mock
    private IdGenerator idGenerator;
    @Mock
    private SystemMapper systemMapper;
    @Mock
    private SystemEntityMapper systemEntityMapper;

    private SystemRepository underTest;

    @BeforeEach
    void setUp() {
        underTest = new MybatisSystemRepository(idGenerator, systemMapper, systemEntityMapper);
    }

    @Test
    void findById() {
        UUID id = UUID.randomUUID();
        SystemEntity mockSystemEntity = mock(SystemEntity.class);
        System mockSystem = mock(System.class);

        when(systemEntityMapper.findById(id)).thenReturn(Optional.of(mockSystemEntity));
        when(systemMapper.map(mockSystemEntity)).thenReturn(mockSystem);

        Optional<System> results = underTest.findById(id);

        verify(systemEntityMapper).findById(id);
        verify(systemMapper).map(mockSystemEntity);
        verifyNoMoreInteractions(systemEntityMapper, systemMapper, idGenerator);

        assertThat(results.isPresent(), is(true));
        assertThat(results.get(), equalTo(mockSystem));
    }

    @Test
    void findByIdNotFound() {
        UUID id = UUID.randomUUID();

        when(systemEntityMapper.findById(id)).thenReturn(Optional.empty());
        Optional<System> result = underTest.findById(id);

        verify(systemEntityMapper).findById(id);
        verify(systemMapper, never()).map(any(SystemEntity.class));
        verifyNoMoreInteractions(systemEntityMapper, systemMapper, idGenerator);

        assertThat(result, equalTo(Optional.empty()));
    }

    @Test
    void createWithoutId() {
        UUID id = UUID.randomUUID();
        System inputSystem = mock(System.class);
        SystemEntity mockSystemEntityWithoutId = mock(SystemEntity.class);
        SystemEntity mockSavedSystemEntity = mock(SystemEntity.class);

        System expected = mock(System.class);

        when(systemMapper.map(inputSystem)).thenReturn(mockSystemEntityWithoutId);
        when(mockSystemEntityWithoutId.getId()).thenReturn(null, id);
        when(idGenerator.generateId()).thenReturn(id);
        when(systemEntityMapper.findById(id)).thenReturn(Optional.of(mockSavedSystemEntity));
        when(systemMapper.map(mockSavedSystemEntity)).thenReturn(expected);

        System result = underTest.create(inputSystem);

        verify(systemMapper).map(inputSystem);
        verify(idGenerator).generateId();
        verify(systemEntityMapper).insert(any());
        verify(systemEntityMapper).findById(id);
        verify(systemMapper).map(mockSavedSystemEntity);
        verifyNoMoreInteractions(systemEntityMapper, systemMapper, idGenerator);

        assertThat(result, is(expected));
    }

    @Test
    void createWithId() {
        UUID id = UUID.randomUUID();
        System inputSystem = mock(System.class);
        SystemEntity mockSystemEntity = mock(SystemEntity.class);

        System expected = mock(System.class);

        when(systemMapper.map(inputSystem)).thenReturn(mockSystemEntity);
        when(mockSystemEntity.getId()).thenReturn(id);
        when(systemEntityMapper.findById(id)).thenReturn(Optional.of(mockSystemEntity));
        when(systemMapper.map(mockSystemEntity)).thenReturn(expected);

        System result = underTest.create(inputSystem);

        verify(systemMapper).map(inputSystem);
        verify(idGenerator, never()).generateId();
        verify(systemEntityMapper).insert(any());
        verify(systemEntityMapper).findById(id);
        verify(systemMapper).map(mockSystemEntity);
        verifyNoMoreInteractions(systemEntityMapper, systemMapper, idGenerator);

        assertThat(result, is(expected));
    }

    @Test
    void createWithError() {
        UUID id = UUID.randomUUID();
        System inputSystem = mock(System.class);
        SystemEntity mockSystemEntity = mock(SystemEntity.class);

        when(systemMapper.map(inputSystem)).thenReturn(mockSystemEntity);
        when(mockSystemEntity.getId()).thenReturn(id);
        when(systemEntityMapper.findById(id)).thenReturn(Optional.empty());

        Exception result = assertThrows(DatabaseEntityNotFoundException.class, () -> underTest.create(inputSystem));

        verify(systemMapper).map(inputSystem);
        verify(idGenerator, never()).generateId();
        verify(systemEntityMapper).insert(any());
        verify(systemEntityMapper).findById(id);
        verifyNoMoreInteractions(systemEntityMapper, systemMapper, idGenerator);

        assertThat(result.getMessage(), is("system with id: " + id + " could not be found after create"));
    }

    @Test
    void findOrCreateByNameNew() {
        String name = "Test System";
        UUID id = UUID.randomUUID();
        SystemEntity mockSystemEntityWithoutId = mock(SystemEntity.class);
        SystemEntity mockSavedSystemEntity = mock(SystemEntity.class);

        System expected = mock(System.class);

        when(systemEntityMapper.findByName(name)).thenReturn(Optional.empty());
        when(systemMapper.map(argThat((System input) -> input.getId() == null && input.getName().equals(name)))).thenReturn(mockSystemEntityWithoutId);
        when(mockSystemEntityWithoutId.getId()).thenReturn(null, id);
        when(idGenerator.generateId()).thenReturn(id);
        when(systemEntityMapper.findById(id)).thenReturn(Optional.of(mockSavedSystemEntity));
        when(systemMapper.map(mockSavedSystemEntity)).thenReturn(expected);

        System result = underTest.findOrCreateByName(name);

        verify(systemEntityMapper).findByName(name);
        verify(systemMapper).map(argThat((System input) -> input.getId() == null && input.getName().equals(name)));
        verify(idGenerator).generateId();
        verify(systemEntityMapper).insert(any());
        verify(systemEntityMapper).findById(id);
        verify(systemMapper).map(mockSavedSystemEntity);
        verifyNoMoreInteractions(systemEntityMapper, systemMapper, idGenerator);

        assertThat(result, is(expected));
    }

    @Test
    void findOrCreateByNameFound() {
        String name = "Test System";
        SystemEntity mockSavedSystemEntity = mock(SystemEntity.class);

        System expected = mock(System.class);

        when(systemEntityMapper.findByName(name)).thenReturn(Optional.of(mockSavedSystemEntity));
        when(systemMapper.map(mockSavedSystemEntity)).thenReturn(expected);

        System result = underTest.findOrCreateByName(name);

        verify(systemEntityMapper).findByName(name);
        verify(systemMapper).map(mockSavedSystemEntity);
        verifyNoMoreInteractions(systemEntityMapper, systemMapper, idGenerator);

        assertThat(result, is(expected));
    }

    @Test
    void findByName() {
        String name = "Test System";
        SystemEntity mockSystemEntity = mock(SystemEntity.class);
        System expected = mock(System.class);

        when(systemEntityMapper.findByName(name)).thenReturn(Optional.of(mockSystemEntity));
        when(systemMapper.map(mockSystemEntity)).thenReturn(expected);

        System result = underTest.findOrCreateByName(name);

        assertThat(result, is(expected));
    }

    @Test
    void findByNameNotFound() {
        String name = "Test System";

        when(systemEntityMapper.findByName(name)).thenReturn(Optional.empty());

        Optional<System> result = underTest.findByName(name);

        assertThat(result.isPresent(), is(false));
    }


    @Test
    void findFromSearchbar() {
        String name = "Test System";
        int amount = 10;
        SystemEntity mockSystemEntity = mock(SystemEntity.class);
        System expected = mock(System.class);

        when(systemEntityMapper.findFromSearchbar(name, amount)).thenReturn(List.of(mockSystemEntity));
        when(systemMapper.map(mockSystemEntity)).thenReturn(expected);

        List<System> result = underTest.findFromSearchbar(name, amount);

        assertThat(result, hasSize(1));
        assertThat(result, contains(expected));
    }

    @Test
    void findFromSearchbarNotFound() {
        String name = "Test System";
        int amount = 10;

        when(systemEntityMapper.findFromSearchbar(name, amount)).thenReturn(Collections.emptyList());

        List<System> result = underTest.findFromSearchbar(name, amount);

        assertThat(result, empty());
    }

}
