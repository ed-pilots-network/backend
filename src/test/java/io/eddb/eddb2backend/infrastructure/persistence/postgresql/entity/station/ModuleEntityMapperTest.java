package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.station;

import io.eddb.eddb2backend.domain.model.station.Module;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ModuleEntityMapperTest {
    private Module module;
    private ModuleEntity moduleEntity;

    @BeforeEach
    public void setUp() {
        module = Module.builder()
                .id(1L)
                .name("Test Module")
                .build();

        moduleEntity = ModuleEntity.builder()
                .id(1L)
                .name("Test Module")
                .build();
    }

    @Test
    public void map_moduleToModuleEntity() {
        Optional<ModuleEntity> mappedEntity = ModuleEntity.Mapper.map(module);
        assertThat(mappedEntity.isPresent(), is(true));
        assertThat(mappedEntity.get(), is(moduleEntity));
    }

    @Test
    public void map_moduleEntityToModule() {
        Optional<Module> mappedModule = ModuleEntity.Mapper.map(moduleEntity);
        assertThat(mappedModule.isPresent(), is(true));
        assertThat(mappedModule.get(), is(module));
    }
}
