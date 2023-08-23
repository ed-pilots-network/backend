package io.edpn.backend.trade.adapter.persistence.system;

import io.edpn.backend.trade.adapter.persistence.SystemRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisPersistenceFindSystemFilterMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemRepository;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.SystemEntityMapper;
import io.edpn.backend.trade.application.port.outgoing.system.LoadOrCreateSystemByNamePort;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoadOrCreateSystemByNamePortTest {
    
    @Mock
    private IdGenerator idGenerator;
    
    @Mock
    private SystemEntityMapper<MybatisSystemEntity> mybatisSystemEntityMapper;
    
    @Mock
    private MybatisPersistenceFindSystemFilterMapper mybatisPersistenceFindSystemFilterMapper;
    
    @Mock
    private MybatisSystemRepository mybatisSystemRepository;
    
    private LoadOrCreateSystemByNamePort underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new SystemRepository(idGenerator, mybatisSystemEntityMapper, mybatisPersistenceFindSystemFilterMapper, mybatisSystemRepository);
    }
    
    @Test
    void findOrCreateByNameNew() {
        String name = "Test System";
        UUID id = UUID.randomUUID();
        MybatisSystemEntity mockSystemEntityWithoutId = mock(MybatisSystemEntity.class);
        MybatisSystemEntity mockSavedSystemEntity = mock(MybatisSystemEntity.class);
        
        System expected = mock(System.class);
        
        when(mybatisSystemRepository.findByName(name)).thenReturn(Optional.empty());
        when(mybatisSystemEntityMapper.map(argThat((System input) -> input.getId() == null && input.getName().equals(name)))).thenReturn(mockSystemEntityWithoutId);
        when(mockSystemEntityWithoutId.getId()).thenReturn(null, id);
        when(idGenerator.generateId()).thenReturn(id);
        when(mybatisSystemRepository.findById(id)).thenReturn(Optional.ofNullable(mockSavedSystemEntity));
        when(mybatisSystemEntityMapper.map(mockSavedSystemEntity)).thenReturn(expected);
        
        System result = underTest.loadOrCreateSystemByName(name);
        
        verify(mybatisSystemRepository).findByName(name);
        verify(mybatisSystemEntityMapper).map(argThat((System input) -> input.getId() == null && input.getName().equals(name)));
        verify(idGenerator).generateId();
        verify(mybatisSystemRepository).insert(any());
        verify(mybatisSystemRepository).findById(id);
        verify(mybatisSystemEntityMapper).map(mockSavedSystemEntity);
        verifyNoMoreInteractions(mybatisSystemRepository, mybatisSystemEntityMapper, idGenerator);
        
        assertThat(result, is(expected));
    }
    
    @Test
    void findOrCreateByNameFound() {
        String name = "Test System";
        MybatisSystemEntity mockSavedSystemEntity = mock(MybatisSystemEntity.class);
        
        System expected = mock(System.class);
        
        when(mybatisSystemRepository.findByName(name)).thenReturn(Optional.of(mockSavedSystemEntity));
        when(mybatisSystemEntityMapper.map(mockSavedSystemEntity)).thenReturn(expected);
        
        System result = underTest.loadOrCreateSystemByName(name);
        
        verify(mybatisSystemRepository).findByName(name);
        verify(mybatisSystemEntityMapper).map(mockSavedSystemEntity);
        verifyNoMoreInteractions(mybatisSystemRepository, mybatisSystemEntityMapper, idGenerator);
        
        assertThat(result, is(expected));
    }
}
