package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.SystemEntity;
import io.edpn.backend.messageprocessor.commodityv3.domain.repository.SystemRepository;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers.SystemEntityMapper;
import io.edpn.backend.messageprocessor.domain.exception.DatabaseEntityNotFoundException;
import io.edpn.backend.messageprocessor.domain.util.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SystemRepositoryTest {
    @Mock
    private IdGenerator idGenerator;
    @Mock
    private SystemEntityMapper systemEntityMapper;

    private SystemRepository systemRepository;

    @BeforeEach
    void setUp() {
        systemRepository = new io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository.SystemRepository(idGenerator, systemEntityMapper);
    }

    @Test
    void findOrCreateByName_existingSystem_returnSystemEntity() {
        String systemName = "Existing System";
        UUID systemId = UUID.randomUUID();
        SystemEntity existingSystem = SystemEntity.builder()
                .id(systemId)
                .name(systemName)
                .build();

        when(systemEntityMapper.findByName(systemName))
                .thenReturn(Optional.of(existingSystem));

        SystemEntity result = systemRepository.findOrCreateByName(systemName);

        verify(systemEntityMapper).findByName(systemName);
        verifyNoMoreInteractions(systemEntityMapper);
        assertEquals(existingSystem, result);
    }

    @Test
    void findOrCreateByName_nonExistingSystem_createAndReturnSystemEntity() {
        String systemName = "New System";
        UUID generatedId = UUID.randomUUID();
        SystemEntity newSystem = SystemEntity.builder()
                .id(generatedId)
                .name(systemName)
                .build();

        when(systemEntityMapper.findByName(systemName))
                .thenReturn(Optional.empty());
        when(idGenerator.generateId()).thenReturn(generatedId);
        when(systemEntityMapper.insert(argThat(arg -> arg.getId().equals(generatedId) && arg.getName().equals(systemName))))
                .thenReturn(1);
        when(systemEntityMapper.findById(generatedId)).thenReturn(Optional.of(newSystem));

        SystemEntity result = systemRepository.findOrCreateByName(systemName);

        verify(systemEntityMapper).findByName(systemName);
        verify(idGenerator).generateId();
        verify(systemEntityMapper).insert(argThat(arg -> arg.getId().equals(generatedId) && arg.getName().equals(systemName)));
        verify(systemEntityMapper).findById(generatedId);
        verifyNoMoreInteractions(systemEntityMapper);
        assertEquals(newSystem, result);
    }

    @Test
    void findOrCreateByName_EntityNotFoundInDatabase_AfterCreateThrowsException_ThrowsDatabaseEntityNotFoundException() {
        when(systemEntityMapper.findByName(any())).thenReturn(Optional.empty());
        when(idGenerator.generateId()).thenReturn(UUID.randomUUID());
        when(systemEntityMapper.insert(any())).thenReturn(1);
        when(systemEntityMapper.findById(any())).thenReturn(Optional.empty());

        assertThrows(DatabaseEntityNotFoundException.class, () -> systemRepository.findOrCreateByName(""));

        verify(systemEntityMapper).findByName(any());
        verify(idGenerator).generateId();
        verify(systemEntityMapper).insert(any());
        verify(systemEntityMapper).findById(any());
        verifyNoMoreInteractions(systemEntityMapper);
    }

    @Test
    void create_systemEntity_knownId_returnCreatedSystemEntity() {
        UUID generatedId = UUID.randomUUID();
        SystemEntity newSystem = SystemEntity.builder()
                .name("New System")
                .id(generatedId)
                .build();

        when(systemEntityMapper.insert(newSystem))
                .thenReturn(1);
        when(systemEntityMapper.findById(generatedId)).thenReturn(Optional.of(newSystem));

        SystemEntity result = systemRepository.create(newSystem);

        verify(systemEntityMapper).insert(newSystem);
        verifyNoMoreInteractions(systemEntityMapper);
        assertEquals(generatedId, result.getId());
        assertEquals(newSystem.getName(), result.getName());
    }

    @Test
    void create_systemEntity_unknownId_returnCreatedSystemEntity() {
        UUID generatedId = UUID.randomUUID();
        String systemName = "New System";
        SystemEntity newSystem = SystemEntity.builder()
                .name(systemName)
                .build();
        SystemEntity newSystem2 = SystemEntity.builder()
                .name(systemName)
                .id(generatedId)
                .build();

        when(idGenerator.generateId()).thenReturn(generatedId);
        when(systemEntityMapper.insert(argThat(arg -> arg.getId().equals(generatedId) && arg.getName().equals(systemName))))
                .thenReturn(1);
        when(systemEntityMapper.findById(generatedId)).thenReturn(Optional.of(newSystem2));

        SystemEntity result = systemRepository.create(newSystem);

        verify(idGenerator).generateId();
        verify(systemEntityMapper).insert(newSystem);
        verifyNoMoreInteractions(systemEntityMapper);
        assertEquals(newSystem.getName(), result.getName());
    }

    @Test
    void create_EntityNotFoundInDatabase_AfterCreateThrowsException_ThrowsDatabaseEntityNotFoundException() {
        // Arrange
        SystemEntity entity = SystemEntity.builder()
                .name("TestSystem")
                .build();

        when(idGenerator.generateId()).thenReturn(UUID.randomUUID());
        when(systemEntityMapper.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Act
        assertThrows(DatabaseEntityNotFoundException.class, () -> systemRepository.create(entity));

        // Assert
        verify(systemEntityMapper, times(1)).insert(entity);
        verify(systemEntityMapper, times(1)).findById(entity.getId());
    }

    @Test
    void findById_existingSystem_returnOptionalSystemEntity() {
        UUID systemId = UUID.randomUUID();
        SystemEntity existingSystem = SystemEntity.builder()
                .id(systemId)
                .name("Existing System")
                .build();

        when(systemEntityMapper.findById(systemId))
                .thenReturn(Optional.of(existingSystem));

        Optional<SystemEntity> result = systemRepository.findById(systemId);

        verify(systemEntityMapper).findById(systemId);
        verifyNoMoreInteractions(systemEntityMapper);
        assertTrue(result.isPresent());
        assertEquals(existingSystem, result.get());
    }

    @Test
    void findById_nonExistingSystem_returnEmptyOptional() {
        UUID systemId = UUID.randomUUID();

        when(systemEntityMapper.findById(systemId))
                .thenReturn(Optional.empty());

        Optional<SystemEntity> result = systemRepository.findById(systemId);

        verify(systemEntityMapper).findById(systemId);
        verifyNoMoreInteractions(systemEntityMapper);
        assertFalse(result.isPresent());
    }
}

