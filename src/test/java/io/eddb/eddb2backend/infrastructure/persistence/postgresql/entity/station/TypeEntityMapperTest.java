package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.station;

import io.eddb.eddb2backend.domain.model.station.Type;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TypeEntityMapperTest {
    private Type type;
    private TypeEntity typeEntity;

    @BeforeEach
    public void setUp() {
        type = Type.builder()
                .id(1L)
                .name("Test Type")
                .build();

        typeEntity = TypeEntity.builder()
                .id(1L)
                .name("Test Type")
                .build();
    }

    @Test
    public void map_typeToTypeEntity() {
        Optional<TypeEntity> mappedEntity = TypeEntity.Mapper.map(type);
        assertThat(mappedEntity.isPresent(), is(true));
        assertThat(mappedEntity.get(), is(typeEntity));
    }

    @Test
    public void map_typeEntityToType() {
        Optional<Type> mappedType = TypeEntity.Mapper.map(typeEntity);
        assertThat(mappedType.isPresent(), is(true));
        assertThat(mappedType.get(), is(type));
    }
}
