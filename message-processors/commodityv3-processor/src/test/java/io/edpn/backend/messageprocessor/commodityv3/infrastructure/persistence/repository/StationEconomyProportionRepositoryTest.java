package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.StationEconomyProportionEntity;
import io.edpn.backend.messageprocessor.commodityv3.domain.repository.StationEconomyProportionRepository;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers.StationEconomyProportionEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StationEconomyProportionRepositoryTest {

    @Mock
    private StationEconomyProportionEntityMapper stationEconomyProportionEntityMapper;

    private StationEconomyProportionRepository repository;

    @BeforeEach
    void setup() {
        repository = new io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository.StationEconomyProportionRepository(stationEconomyProportionEntityMapper);
    }

    @Test
    void testDeleteByStationId() {
        UUID stationId = UUID.randomUUID();

        repository.deleteByStationId(stationId);

        verify(stationEconomyProportionEntityMapper, times(1)).delete(stationId);
    }

    @Test
    void testInsert() {
        StationEconomyProportionEntity entity = new StationEconomyProportionEntity();
        List<StationEconomyProportionEntity> entities = Collections.singletonList(entity);

        repository.insert(entities);

        verify(stationEconomyProportionEntityMapper, times(1)).insert(entity);
    }
}
