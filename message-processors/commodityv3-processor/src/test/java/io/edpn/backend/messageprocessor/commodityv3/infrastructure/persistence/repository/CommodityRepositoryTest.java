package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.CommodityEntity;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers.CommodityEntityMapper;
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
class CommodityRepositoryTest {
    @Mock
    private IdGenerator idGenerator;
    @Mock
    private CommodityEntityMapper commodityEntityMapper;

    private CommodityRepository commodityRepository;

    @BeforeEach
    void setUp() {
        commodityRepository = new CommodityRepository(idGenerator, commodityEntityMapper);
    }

    @Test
    void findOrCreateByName_existingCommodity_returnCommodityEntity() {
        String commodityName = "Existing Commodity";
        UUID commodityId = UUID.randomUUID();
        CommodityEntity existingCommodity = CommodityEntity.builder()
                .id(commodityId)
                .name(commodityName)
                .build();

        when(commodityEntityMapper.findByName(commodityName))
                .thenReturn(Optional.of(existingCommodity));

        CommodityEntity result = commodityRepository.findOrCreateByName(commodityName);

        verify(commodityEntityMapper).findByName(commodityName);
        verifyNoMoreInteractions(commodityEntityMapper);
        assertEquals(existingCommodity, result);
    }

    @Test
    void findOrCreateByName_nonExistingCommodity_createAndReturnCommodityEntity() {
        String commodityName = "New Commodity";
        UUID generatedId = UUID.randomUUID();
        CommodityEntity newCommodity = CommodityEntity.builder()
                .id(generatedId)
                .name(commodityName)
                .build();

        when(commodityEntityMapper.findByName(commodityName))
                .thenReturn(Optional.empty());
        when(idGenerator.generateId()).thenReturn(generatedId);
        when(commodityEntityMapper.insert(argThat(arg -> arg.getId().equals(generatedId) && arg.getName().equals(commodityName))))
                .thenReturn(1);
        when(commodityEntityMapper.findById(generatedId)).thenReturn(Optional.of(newCommodity));

        CommodityEntity result = commodityRepository.findOrCreateByName(commodityName);

        verify(commodityEntityMapper).findByName(commodityName);
        verify(idGenerator).generateId();
        verify(commodityEntityMapper).insert(argThat(arg -> arg.getId().equals(generatedId) && arg.getName().equals(commodityName)));
        verify(commodityEntityMapper).findById(generatedId);
        verifyNoMoreInteractions(commodityEntityMapper);
        assertEquals(newCommodity, result);
    }

    @Test
    void findOrCreateByName_EntityNotFoundInDatabase_AfterCreateThrowsException_ThrowsDatabaseEntityNotFoundException() {
        when(commodityEntityMapper.findByName(any())).thenReturn(Optional.empty());
        when(idGenerator.generateId()).thenReturn(UUID.randomUUID());
        when(commodityEntityMapper.insert(any())).thenReturn(1);
        when(commodityEntityMapper.findById(any())).thenReturn(Optional.empty());

        assertThrows(DatabaseEntityNotFoundException.class, () -> commodityRepository.findOrCreateByName(""));

        verify(commodityEntityMapper).findByName(any());
        verify(idGenerator).generateId();
        verify(commodityEntityMapper).insert(any());
        verify(commodityEntityMapper).findById(any());
        verifyNoMoreInteractions(commodityEntityMapper);
    }

    @Test
    void create_commodityEntity_knownId_returnCreatedCommodityEntity() {
        UUID generatedId = UUID.randomUUID();
        CommodityEntity newCommodity = CommodityEntity.builder()
                .name("New Commodity")
                .id(generatedId)
                .build();

        when(commodityEntityMapper.insert(newCommodity))
                .thenReturn(1);
        when(commodityEntityMapper.findById(generatedId)).thenReturn(Optional.of(newCommodity));

        CommodityEntity result = commodityRepository.create(newCommodity);

        verify(commodityEntityMapper).insert(newCommodity);
        verifyNoMoreInteractions(commodityEntityMapper);
        assertEquals(generatedId, result.getId());
        assertEquals(newCommodity.getName(), result.getName());
    }

    @Test
    void create_commodityEntity_unknownId_returnCreatedCommodityEntity() {
        UUID generatedId = UUID.randomUUID();
        String commodityName = "New Commodity";
        CommodityEntity newCommodity = CommodityEntity.builder()
                .name(commodityName)
                .build();
        CommodityEntity newCommodity2 = CommodityEntity.builder()
                .name(commodityName)
                .id(generatedId)
                .build();

        when(idGenerator.generateId()).thenReturn(generatedId);
        when(commodityEntityMapper.insert(argThat(arg -> arg.getId().equals(generatedId) && arg.getName().equals(commodityName))))
                .thenReturn(1);
        when(commodityEntityMapper.findById(generatedId)).thenReturn(Optional.of(newCommodity2));

        CommodityEntity result = commodityRepository.create(newCommodity);

        verify(idGenerator).generateId();
        verify(commodityEntityMapper).insert(newCommodity);
        verifyNoMoreInteractions(commodityEntityMapper);
        assertEquals(newCommodity.getName(), result.getName());
    }

    @Test
    void create_EntityNotFoundInDatabase_AfterCreateThrowsException_ThrowsDatabaseEntityNotFoundException() {
        // Arrange
        CommodityEntity entity = CommodityEntity.builder()
                .name("TestCommodity")
                .build();

        when(idGenerator.generateId()).thenReturn(UUID.randomUUID());
        when(commodityEntityMapper.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Act
        assertThrows(DatabaseEntityNotFoundException.class, () -> commodityRepository.create(entity));

        // Assert
        verify(commodityEntityMapper, times(1)).insert(entity);
        verify(commodityEntityMapper, times(1)).findById(entity.getId());
    }

    @Test
    void findById_existingCommodity_returnOptionalCommodityEntity() {
        UUID commodityId = UUID.randomUUID();
        CommodityEntity existingCommodity = CommodityEntity.builder()
                .id(commodityId)
                .name("Existing Commodity")
                .build();

        when(commodityEntityMapper.findById(commodityId))
                .thenReturn(Optional.of(existingCommodity));

        Optional<CommodityEntity> result = commodityRepository.findById(commodityId);

        verify(commodityEntityMapper).findById(commodityId);
        verifyNoMoreInteractions(commodityEntityMapper);
        assertTrue(result.isPresent());
        assertEquals(existingCommodity, result.get());
    }

    @Test
    void findById_nonExistingCommodity_returnEmptyOptional() {
        UUID commodityId = UUID.randomUUID();

        when(commodityEntityMapper.findById(commodityId))
                .thenReturn(Optional.empty());

        Optional<CommodityEntity> result = commodityRepository.findById(commodityId);

        verify(commodityEntityMapper).findById(commodityId);
        verifyNoMoreInteractions(commodityEntityMapper);
        assertFalse(result.isPresent());
    }
}

