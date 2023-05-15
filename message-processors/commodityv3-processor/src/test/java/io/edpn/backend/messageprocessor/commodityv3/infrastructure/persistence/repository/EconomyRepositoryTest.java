package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.EconomyEntity;
import io.edpn.backend.messageprocessor.commodityv3.domain.repository.EconomyRepository;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers.EconomyEntityMapper;
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
class EconomyRepositoryTest {
    @Mock
    private IdGenerator idGenerator;
    @Mock
    private EconomyEntityMapper economyEntityMapper;

    private EconomyRepository economyRepository;

    @BeforeEach
    void setUp() {
        economyRepository = new io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository.EconomyRepository(idGenerator, economyEntityMapper);
    }

    @Test
    void findOrCreateByName_existingEconomy_returnEconomyEntity() {
        String economyName = "Existing Economy";
        UUID economyId = UUID.randomUUID();
        EconomyEntity existingEconomy = EconomyEntity.builder()
                .id(economyId)
                .name(economyName)
                .build();

        when(economyEntityMapper.findByName(economyName))
                .thenReturn(Optional.of(existingEconomy));

        EconomyEntity result = economyRepository.findOrCreateByName(economyName);

        verify(economyEntityMapper).findByName(economyName);
        verifyNoMoreInteractions(economyEntityMapper);
        assertEquals(existingEconomy, result);
    }

    @Test
    void findOrCreateByName_nonExistingEconomy_createAndReturnEconomyEntity() {
        String economyName = "New Economy";
        UUID generatedId = UUID.randomUUID();
        EconomyEntity newEconomy = EconomyEntity.builder()
                .id(generatedId)
                .name(economyName)
                .build();

        when(economyEntityMapper.findByName(economyName))
                .thenReturn(Optional.empty());
        when(idGenerator.generateId()).thenReturn(generatedId);
        when(economyEntityMapper.insert(argThat(arg -> arg.getId().equals(generatedId) && arg.getName().equals(economyName))))
                .thenReturn(1);
        when(economyEntityMapper.findById(generatedId)).thenReturn(Optional.of(newEconomy));

        EconomyEntity result = economyRepository.findOrCreateByName(economyName);

        verify(economyEntityMapper).findByName(economyName);
        verify(idGenerator).generateId();
        verify(economyEntityMapper).insert(argThat(arg -> arg.getId().equals(generatedId) && arg.getName().equals(economyName)));
        verify(economyEntityMapper).findById(generatedId);
        verifyNoMoreInteractions(economyEntityMapper);
        assertEquals(newEconomy, result);
    }

    @Test
    void findOrCreateByName_EntityNotFoundInDatabase_AfterCreateThrowsException_ThrowsDatabaseEntityNotFoundException() {
        when(economyEntityMapper.findByName(any())).thenReturn(Optional.empty());
        when(idGenerator.generateId()).thenReturn(UUID.randomUUID());
        when(economyEntityMapper.insert(any())).thenReturn(1);
        when(economyEntityMapper.findById(any())).thenReturn(Optional.empty());

        assertThrows(DatabaseEntityNotFoundException.class, () -> economyRepository.findOrCreateByName(""));

        verify(economyEntityMapper).findByName(any());
        verify(idGenerator).generateId();
        verify(economyEntityMapper).insert(any());
        verify(economyEntityMapper).findById(any());
        verifyNoMoreInteractions(economyEntityMapper);
    }

    @Test
    void create_economyEntity_knownId_returnCreatedEconomyEntity() {
        UUID generatedId = UUID.randomUUID();
        EconomyEntity newEconomy = EconomyEntity.builder()
                .name("New Economy")
                .id(generatedId)
                .build();

        when(economyEntityMapper.insert(newEconomy))
                .thenReturn(1);
        when(economyEntityMapper.findById(generatedId)).thenReturn(Optional.of(newEconomy));

        EconomyEntity result = economyRepository.create(newEconomy);

        verify(economyEntityMapper).insert(newEconomy);
        verifyNoMoreInteractions(economyEntityMapper);
        assertEquals(generatedId, result.getId());
        assertEquals(newEconomy.getName(), result.getName());
    }

    @Test
    void create_economyEntity_unknownId_returnCreatedEconomyEntity() {
        UUID generatedId = UUID.randomUUID();
        String economyName = "New Economy";
        EconomyEntity newEconomy = EconomyEntity.builder()
                .name(economyName)
                .build();
        EconomyEntity newEconomy2 = EconomyEntity.builder()
                .name(economyName)
                .id(generatedId)
                .build();

        when(idGenerator.generateId()).thenReturn(generatedId);
        when(economyEntityMapper.insert(argThat(arg -> arg.getId().equals(generatedId) && arg.getName().equals(economyName))))
                .thenReturn(1);
        when(economyEntityMapper.findById(generatedId)).thenReturn(Optional.of(newEconomy2));

        EconomyEntity result = economyRepository.create(newEconomy);

        verify(idGenerator).generateId();
        verify(economyEntityMapper).insert(newEconomy);
        verifyNoMoreInteractions(economyEntityMapper);
        assertEquals(newEconomy.getName(), result.getName());
    }

    @Test
    void create_EntityNotFoundInDatabase_AfterCreateThrowsException_ThrowsDatabaseEntityNotFoundException() {
        // Arrange
        EconomyEntity entity = EconomyEntity.builder()
                .name("TestEconomy")
                .build();

        when(idGenerator.generateId()).thenReturn(UUID.randomUUID());
        when(economyEntityMapper.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Act
        assertThrows(DatabaseEntityNotFoundException.class, () -> economyRepository.create(entity));

        // Assert
        verify(economyEntityMapper, times(1)).insert(entity);
        verify(economyEntityMapper, times(1)).findById(entity.getId());
    }

    @Test
    void findById_existingEconomy_returnOptionalEconomyEntity() {
        UUID economyId = UUID.randomUUID();
        EconomyEntity existingEconomy = EconomyEntity.builder()
                .id(economyId)
                .name("Existing Economy")
                .build();

        when(economyEntityMapper.findById(economyId))
                .thenReturn(Optional.of(existingEconomy));

        Optional<EconomyEntity> result = economyRepository.findById(economyId);

        verify(economyEntityMapper).findById(economyId);
        verifyNoMoreInteractions(economyEntityMapper);
        assertTrue(result.isPresent());
        assertEquals(existingEconomy, result.get());
    }

    @Test
    void findById_nonExistingEconomy_returnEmptyOptional() {
        UUID economyId = UUID.randomUUID();

        when(economyEntityMapper.findById(economyId))
                .thenReturn(Optional.empty());

        Optional<EconomyEntity> result = economyRepository.findById(economyId);

        verify(economyEntityMapper).findById(economyId);
        verifyNoMoreInteractions(economyEntityMapper);
        assertFalse(result.isPresent());
    }
}

