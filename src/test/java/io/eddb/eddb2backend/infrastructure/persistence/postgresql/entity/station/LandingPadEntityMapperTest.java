package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.station;

import io.eddb.eddb2backend.domain.model.station.LandingPad;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LandingPadEntityMapperTest {
    private LandingPad landingPad;
    private LandingPadEntity landingPadEntity;

    @BeforeEach
    public void setUp() {
        landingPad = LandingPad.builder()
                .id(1L)
                .size('L')
                .build();

        landingPadEntity = LandingPadEntity.builder()
                .id(1L)
                .size('L')
                .build();
    }

    @Test
    public void map_landingPadToLandingPadEntity() {
        Optional<LandingPadEntity> mappedEntity = LandingPadEntity.Mapper.map(landingPad);
        assertThat(mappedEntity.isPresent(), is(true));
        assertThat(mappedEntity.get(), is(landingPadEntity));
    }

    @Test
    public void map_landingPadEntityToLandingPad() {
        Optional<LandingPad> mappedLandingPad = LandingPadEntity.Mapper.map(landingPadEntity);
        assertThat(mappedLandingPad.isPresent(), is(true));
        assertThat(mappedLandingPad.get(), is(landingPad));
    }
}
