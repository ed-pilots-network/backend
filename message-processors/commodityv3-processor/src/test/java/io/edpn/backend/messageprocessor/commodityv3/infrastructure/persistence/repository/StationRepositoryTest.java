package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.StationEntity;
import io.edpn.backend.messageprocessor.commodityv3.domain.repository.StationRepository;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers.StationEntityMapper;
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
class StationRepositoryTest {

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private StationEntityMapper stationEntityMapper;

    private StationRepository stationRepository;

    @BeforeEach
    void setUp() {
        stationRepository = new io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository.StationRepository(idGenerator, stationEntityMapper);
    }

    @Test
    void findByMarketId_StationEntityExists_ReturnsOptionalStationEntity() {
        // Arrange
        long marketId = 12345L;
        StationEntity stationEntity = new StationEntity();
        when(stationEntityMapper.findByMarketId(marketId)).thenReturn(Optional.of(stationEntity));

        // Act
        Optional<StationEntity> result = stationRepository.findByMarketId(marketId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(stationEntity, result.get());
    }

    @Test
    void findByMarketId_StationEntityDoesNotExist_ReturnsEmptyOptional() {
        // Arrange
        long marketId = 12345L;
        when(stationEntityMapper.findByMarketId(marketId)).thenReturn(Optional.empty());

        // Act
        Optional<StationEntity> result = stationRepository.findByMarketId(marketId);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void findOrCreateBySystemIdAndStationName_StationEntityExists_ReturnsStationEntity() throws DatabaseEntityNotFoundException {
        // Arrange
        UUID systemId = UUID.randomUUID();
        String stationName = "Test Station";
        StationEntity stationEntity = new StationEntity();
        when(stationEntityMapper.findBySystemIdAndStationName(systemId, stationName)).thenReturn(Optional.of(stationEntity));

        // Act
        StationEntity result = stationRepository.findOrCreateBySystemIdAndStationName(systemId, stationName);

        // Assert
        assertEquals(stationEntity, result);
        verify(stationEntityMapper, never()).insert(any(StationEntity.class));
    }

    @Test
    void findOrCreateBySystemIdAndStationName_StationEntityDoesNotExist_CreatesAndReturnsNewStationEntity() throws DatabaseEntityNotFoundException {
        // Arrange
        UUID systemId = UUID.randomUUID();
        String stationName = "Test Station";
        StationEntity stationEntity = new StationEntity();
        when(stationEntityMapper.findBySystemIdAndStationName(systemId, stationName)).thenReturn(Optional.empty());
        when(stationEntityMapper.insert(any(StationEntity.class))).thenReturn(1);
        when(idGenerator.generateId()).thenReturn(UUID.randomUUID());
        when(stationEntityMapper.findById(any(UUID.class))).thenReturn(Optional.of(stationEntity));

        // Act
        StationEntity result = stationRepository.findOrCreateBySystemIdAndStationName(systemId, stationName);

        // Assert
        assertEquals(stationEntity, result);
    }

    @Test
    void update_StationEntityExists_ReturnsUpdatedStationEntity() throws DatabaseEntityNotFoundException {
        // Arrange
        UUID entityId = UUID.randomUUID();
        StationEntity existingEntity = new StationEntity();
        existingEntity.setId(entityId);
        StationEntity updatedEntity = new StationEntity();
        updatedEntity.setId(entityId);
        when(stationEntityMapper.update(updatedEntity)).thenReturn(1);
        when(stationEntityMapper.findById(entityId)).thenReturn(Optional.of(updatedEntity));
        // Act
        StationEntity result = stationRepository.update(updatedEntity);

        // Assert
        assertEquals(updatedEntity, result);
    }

    @Test
    void update_StationEntityDoesNotExist_ThrowsDatabaseEntityNotFoundException() {
        // Arrange
        UUID entityId = UUID.randomUUID();
        StationEntity entity = new StationEntity();
        entity.setId(entityId);
        when(stationEntityMapper.update(entity)).thenReturn(0);

        // Act & Assert
        assertThrows(DatabaseEntityNotFoundException.class, () -> stationRepository.update(entity));
    }

    @Test
    void create_StationEntityCreatedSuccessfully_ReturnsCreatedStationEntity() throws DatabaseEntityNotFoundException {
        // Arrange
        UUID entityId = UUID.randomUUID();
        StationEntity entity = new StationEntity();
        when(idGenerator.generateId()).thenReturn(entityId);
        when(stationEntityMapper.insert(entity)).thenReturn(1);
        when(stationEntityMapper.findById(entityId)).thenReturn(Optional.of(entity));

        // Act
        StationEntity result = stationRepository.create(entity);

        // Assert
        assertEquals(entity, result);
    }

    @Test
    void create_StationEntityCreatedSuccessfully_knownId_ReturnsCreatedStationEntity() throws DatabaseEntityNotFoundException {
        // Arrange
        UUID entityId = UUID.randomUUID();
        StationEntity entity = new StationEntity();
        entity.setId(entityId);
        when(stationEntityMapper.insert(entity)).thenReturn(1);
        when(stationEntityMapper.findById(entityId)).thenReturn(Optional.of(entity));

        // Act
        StationEntity result = stationRepository.create(entity);

        // Assert
        assertEquals(entity, result);
    }

    @Test
    void create_StationEntityCreationFails_ThrowsDatabaseEntityNotFoundException() {
        // Arrange
        when(idGenerator.generateId()).thenReturn(UUID.randomUUID());
        when(stationEntityMapper.insert(any())).thenReturn(0);
        when(stationEntityMapper.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DatabaseEntityNotFoundException.class, () -> stationRepository.create(new StationEntity()));
    }

    @Test
    void findById_StationEntityExists_ReturnsOptionalStationEntity() {
        // Arrange
        UUID entityId = UUID.randomUUID();
        StationEntity stationEntity = new StationEntity();
        when(stationEntityMapper.findById(entityId)).thenReturn(Optional.of(stationEntity));

        // Act
        Optional<StationEntity> result = stationRepository.findById(entityId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(stationEntity, result.get());
    }

    @Test
    void findById_StationEntityDoesNotExist_ReturnsEmptyOptional() {
        // Arrange
        UUID entityId = UUID.randomUUID();
        when(stationEntityMapper.findById(entityId)).thenReturn(Optional.empty());

        // Act
        Optional<StationEntity> result = stationRepository.findById(entityId);

        // Assert
        assertFalse(result.isPresent());
    }
}
