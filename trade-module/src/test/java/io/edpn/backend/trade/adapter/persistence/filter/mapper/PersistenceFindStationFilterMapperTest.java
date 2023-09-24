package io.edpn.backend.trade.adapter.persistence.filter.mapper;

import io.edpn.backend.trade.application.domain.filter.FindStationFilter;
import io.edpn.backend.trade.application.dto.persistence.filter.PersistenceFindStationFilter;
import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistenceFindStationFilterMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class PersistenceFindStationFilterMapperTest {

    private PersistenceFindStationFilterMapper underTest;

    @BeforeEach
    public void setUp() {
        underTest = new MybatisPersistenceFindStationFilterMapper();
    }

    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {

        FindStationFilter domainObject = FindStationFilter.builder()
                .hasRequiredOdyssey(false)
                .hasLandingPadSize(false)
                .hasPlanetary(false)
                .hasArrivalDistance(false)
                .build();

        PersistenceFindStationFilter entity = underTest.map(domainObject);

        assertThat(entity.getHasRequiredOdyssey(), is(false));
        assertThat(entity.getHasLandingPadSize(), is(false));
        assertThat(entity.getHasPlanetary(), is(false));
        assertThat(entity.getHasArrivalDistance(), is(false));
    }
}