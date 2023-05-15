package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.HistoricStationCommodityMarketDatumEntity;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers.HistoricStationCommodityMarketDatumEntityMapper;
import io.edpn.backend.messageprocessor.domain.exception.DatabaseEntityNotFoundException;
import io.edpn.backend.messageprocessor.commodityv3.domain.repository.HistoricStationCommodityMarketDatumRepository;
import io.edpn.backend.messageprocessor.domain.util.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MybatisHistoricStationCommodityMarketDatumRepositoryTest {

    private HistoricStationCommodityMarketDatumRepository repository;

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private HistoricStationCommodityMarketDatumEntityMapper mapper;

    @BeforeEach
    void setUp() {
        repository = new MybatisHistoricStationCommodityMarketDatumRepository(idGenerator, mapper);
    }

    @Test
    void create_ValidEntity_unknown_EntityCreatedSuccessfully() throws DatabaseEntityNotFoundException {
        // Arrange
        HistoricStationCommodityMarketDatumEntity entity = buildTestEntity();
        entity.setId(null);
        UUID generatedId = UUID.randomUUID();
        when(idGenerator.generateId()).thenReturn(generatedId);
        when(mapper.findById(generatedId)).thenReturn(Optional.of(entity));

        // Act
        HistoricStationCommodityMarketDatumEntity createdEntity = repository.create(entity);

        // Assert
        assertNotNull(createdEntity);
        assertEquals(generatedId, createdEntity.getId());
        verify(idGenerator, times(1)).generateId();
        verify(mapper, times(1)).insert(entity);
        verify(mapper, times(1)).findById(generatedId);
    }

    @Test
    void create_ValidEntity_knowId_EntityCreatedSuccessfully() throws DatabaseEntityNotFoundException {
        // Arrange
        HistoricStationCommodityMarketDatumEntity entity = buildTestEntity();
        UUID generatedId = entity.getId();
        when(mapper.findById(generatedId)).thenReturn(Optional.of(entity));

        // Act
        HistoricStationCommodityMarketDatumEntity createdEntity = repository.create(entity);

        // Assert
        assertNotNull(createdEntity);
        assertEquals(generatedId, createdEntity.getId());
        verify(mapper, times(1)).insert(entity);
        verify(mapper, times(1)).findById(generatedId);
    }

    @Test
    void getByStationIdAndCommodityIdAndTimestamp_ExistingEntity_ReturnsOptionalOfEntity() {
        // Arrange
        UUID stationId = UUID.randomUUID();
        UUID commodityId = UUID.randomUUID();
        LocalDateTime timestamp = LocalDateTime.now();
        HistoricStationCommodityMarketDatumEntity expectedEntity = buildTestEntity();
        when(mapper.findByStationIdAndCommodityIdAndTimestamp(stationId, commodityId, timestamp)).thenReturn(Optional.of(expectedEntity));

        // Act
        Optional<HistoricStationCommodityMarketDatumEntity> result = repository.getByStationIdAndCommodityIdAndTimestamp(stationId, commodityId, timestamp);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedEntity, result.get());
        verify(mapper, times(1)).findByStationIdAndCommodityIdAndTimestamp(stationId, commodityId, timestamp);
    }

    @Test
    void getById_ExistingEntity_ReturnsOptionalOfEntity() {
        // Arrange
        UUID entityId = UUID.randomUUID();
        HistoricStationCommodityMarketDatumEntity expectedEntity = buildTestEntity();
        when(mapper.findById(entityId)).thenReturn(Optional.of(expectedEntity));

        // Act
        Optional<HistoricStationCommodityMarketDatumEntity> result = repository.getById(entityId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedEntity, result.get());
        verify(mapper, times(1)).findById(entityId);
    }

    @Test
    void cleanupRedundantData_ValidArguments_CallsMapperDeleteObsoleteInBetweenValues() {
        // Arrange
        UUID stationId = UUID.randomUUID();
        UUID commodityId = UUID.randomUUID();

        // Act
        repository.cleanupRedundantData(stationId, commodityId);

        // Assert
        verify(mapper, times(1)).deleteObsoleteForStationAndCommodity(stationId, commodityId);
    }

    @Test
    void findByCommodityIdAndTimestampBetween_ValidArguments_ReturnsCollectionOfEntities() {
        // Arrange
        UUID commodityId = UUID.randomUUID();
        LocalDateTime startTimestamp = LocalDateTime.now();
        LocalDateTime endTimestamp = LocalDateTime.now().plusHours(1);
        HistoricStationCommodityMarketDatumEntity entity1 = buildTestEntity();
        HistoricStationCommodityMarketDatumEntity entity2 = buildTestEntity();
        when(mapper.findByCommodityIdAndTimestampBetween(commodityId, startTimestamp, endTimestamp))
                .thenReturn(List.of(entity1, entity2));

        // Act
        Collection<HistoricStationCommodityMarketDatumEntity> result = repository.findByCommodityIdAndTimestampBetween(commodityId, startTimestamp, endTimestamp);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(entity1));
        assertTrue(result.contains(entity2));
        verify(mapper, times(1)).findByCommodityIdAndTimestampBetween(commodityId, startTimestamp, endTimestamp);
    }

    private HistoricStationCommodityMarketDatumEntity buildTestEntity() {
        // Build and return a test entity object
        return new HistoricStationCommodityMarketDatumEntity(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDateTime.now(),
                100,
                50,
                200,
                1,
                75,
                80,
                4,
                List.of("flag1", "flag2")
        );
    }
}
