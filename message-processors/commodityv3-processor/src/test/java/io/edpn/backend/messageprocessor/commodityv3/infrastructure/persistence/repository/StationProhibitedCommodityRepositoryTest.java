package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.StationProhibitedCommodityEntity;
import io.edpn.backend.messageprocessor.commodityv3.domain.repository.StationProhibitedCommodityRepository;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers.StationProhibitedCommodityEntityMapper;
import io.edpn.backend.messageprocessor.domain.exception.DatabaseEntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StationProhibitedCommodityRepositoryTest {

    @Mock
    private StationProhibitedCommodityEntityMapper entityMapper;

    private StationProhibitedCommodityRepository repository;

    @BeforeEach
    void setup() {
        repository = new io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository.StationProhibitedCommodityRepository(entityMapper);
    }

    @Test
    void testInsert() throws DatabaseEntityNotFoundException {
        UUID stationId = UUID.randomUUID();
        List<UUID> commodityIds = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());

        List<StationProhibitedCommodityEntity> insertedEntities = commodityIds.stream()
                .map(commodityId -> new StationProhibitedCommodityEntity(stationId, commodityId))
                .toList();
        when(entityMapper.insert(argThat(item -> commodityIds.contains(item.getCommodityId())))).thenReturn(1);
        when(entityMapper.findByStationIds(stationId)).thenReturn(insertedEntities);

        Collection<StationProhibitedCommodityEntity> result = repository.insert(stationId, commodityIds);

        verify(entityMapper, times(2)).insert(any());
        verify(entityMapper).findByStationIds(eq(stationId));
        assertEquals(insertedEntities, result);
    }

    @Test
    void testDelete() {
        UUID stationId = UUID.randomUUID();

        repository.deleteByStationId(stationId);

        verify(entityMapper).delete(eq(stationId));
    }
}
