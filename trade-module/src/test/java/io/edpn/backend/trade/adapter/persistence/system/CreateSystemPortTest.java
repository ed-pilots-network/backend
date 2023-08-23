package io.edpn.backend.trade.adapter.persistence.system;

import io.edpn.backend.trade.adapter.persistence.SystemRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisPersistenceFindSystemFilterMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemRepository;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.SystemEntityMapper;
import io.edpn.backend.trade.application.port.outgoing.system.CreateSystemPort;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateSystemPortTest {
    
    @Mock
    private IdGenerator idGenerator;
    
    @Mock
    private SystemEntityMapper<MybatisSystemEntity> mybatisSystemEntityMapper;
    
    @Mock
    private MybatisPersistenceFindSystemFilterMapper mybatisPersistenceFindSystemFilterMapper;
    
    @Mock
    private MybatisSystemRepository mybatisSystemRepository;
    
    private CreateSystemPort underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new SystemRepository(idGenerator, mybatisSystemEntityMapper, mybatisPersistenceFindSystemFilterMapper ,mybatisSystemRepository);
    }
    
    @Test
    void createWithoutId() {
        UUID id = UUID.randomUUID();
        System inputSystem = mock(System.class);
        MybatisSystemEntity mockSystemEntityWithoutId = mock(MybatisSystemEntity.class);
        MybatisSystemEntity mockSavedSystemEntity = mock(MybatisSystemEntity.class);
        
        System expected = mock(System.class);
        
        when(mybatisSystemEntityMapper.map(inputSystem)).thenReturn(mockSystemEntityWithoutId);
        when(mockSystemEntityWithoutId.getId()).thenReturn(null, id);
        when(idGenerator.generateId()).thenReturn(id);
        when(mybatisSystemRepository.findById(id)).thenReturn(Optional.ofNullable(mockSavedSystemEntity));
        when(mybatisSystemEntityMapper.map(mockSavedSystemEntity)).thenReturn(expected);
        
        System result = underTest.create(inputSystem);
        
        verify(mybatisSystemEntityMapper).map(inputSystem);
        verify(idGenerator).generateId();
        verify(mybatisSystemRepository).insert(any());
        verify(mybatisSystemRepository).findById(id);
        verify(mybatisSystemEntityMapper).map(mockSavedSystemEntity);
        verifyNoMoreInteractions(mybatisSystemRepository, mybatisSystemEntityMapper, idGenerator);
        
        assertThat(result, is(expected));
    }
    
    @Test
    void createWithId() {
        UUID id = UUID.randomUUID();
        System inputSystem = mock(System.class);
        MybatisSystemEntity mockSystemEntity = mock(MybatisSystemEntity.class);
        
        System expected = mock(System.class);
        
        when(mybatisSystemEntityMapper.map(inputSystem)).thenReturn(mockSystemEntity);
        when(mockSystemEntity.getId()).thenReturn(id);
        when(mybatisSystemRepository.findById(id)).thenReturn(Optional.of(mockSystemEntity));
        when(mybatisSystemEntityMapper.map(mockSystemEntity)).thenReturn(expected);
        
        System result = underTest.create(inputSystem);
        
        verify(mybatisSystemEntityMapper).map(inputSystem);
        verify(idGenerator, never()).generateId();
        verify(mybatisSystemRepository).insert(any());
        verify(mybatisSystemRepository).findById(id);
        verify(mybatisSystemEntityMapper).map(mockSystemEntity);
        verifyNoMoreInteractions(mybatisSystemRepository, mybatisSystemEntityMapper, idGenerator);
        
        assertThat(result, is(expected));
    }
    
    @Test
    void createWithError() {
        UUID id = UUID.randomUUID();
        System inputSystem = mock(System.class);
        MybatisSystemEntity mockSystemEntity = mock(MybatisSystemEntity.class);
        
        when(mybatisSystemEntityMapper.map(inputSystem)).thenReturn(mockSystemEntity);
        when(mockSystemEntity.getId()).thenReturn(id);
        when(mybatisSystemRepository.findById(id)).thenReturn(Optional.empty());
        
        Exception result = assertThrows(DatabaseEntityNotFoundException.class, () -> underTest.create(inputSystem));
        
        verify(mybatisSystemEntityMapper).map(inputSystem);
        verify(idGenerator, never()).generateId();
        verify(mybatisSystemRepository).insert(any());
        verify(mybatisSystemRepository).findById(id);
        verifyNoMoreInteractions(mybatisSystemRepository, mybatisSystemEntityMapper, idGenerator);
        
        assertThat(result.getMessage(), is("system with id: " + id + " could not be found after create"));
    }
}
