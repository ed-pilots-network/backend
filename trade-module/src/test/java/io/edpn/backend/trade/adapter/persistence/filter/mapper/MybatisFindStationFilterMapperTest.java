package io.edpn.backend.trade.adapter.persistence.filter.mapper;

import io.edpn.backend.trade.adapter.persistence.filter.MybatisFindStationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class MybatisFindStationFilterMapperTest {

    private MybatisFindStationFilterMapper underTest;

    @BeforeEach
    public void setUp() {
        underTest = new MybatisFindStationFilterMapper();
    }

    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {

        io.edpn.backend.trade.application.domain.filter.FindStationFilter domainObject = io.edpn.backend.trade.application.domain.filter.FindStationFilter.builder()
                .hasRequiredOdyssey(false)
                .hasLandingPadSize(false)
                .hasPlanetary(false)
                .hasArrivalDistance(false)
                .build();

        MybatisFindStationFilter entity = underTest.map(domainObject);

        assertThat(entity.getHasRequiredOdyssey(), is(false));
        assertThat(entity.getHasLandingPadSize(), is(false));
        assertThat(entity.getHasPlanetary(), is(false));
        assertThat(entity.getHasArrivalDistance(), is(false));
    }
}