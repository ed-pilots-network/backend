package io.edpn.backend.exploration.adapter.persistence.system;

import io.edpn.backend.exploration.adapter.persistence.MybatisSystemRepository;
import io.edpn.backend.exploration.adapter.persistence.SystemRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.mapper.SystemEntityMapper;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.util.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoadSystemPortTest {
    @Mock
    private MybatisSystemRepository mybatisSystemRepository;

    @Mock
    private SystemEntityMapper<MybatisSystemEntity> systemEntityMapper;

    @Mock
    private IdGenerator idGenerator;

    private LoadSystemPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemRepository(mybatisSystemRepository, systemEntityMapper, idGenerator);
    }

    @Test
    void load_shouldFindByNameAndMap() {

        String name = "system";
        MybatisSystemEntity entity = mock(MybatisSystemEntity.class);
        System mapped = mock(System.class);
        when(mybatisSystemRepository.findByName(name)).thenReturn(Optional.of(entity));
        when(systemEntityMapper.map(entity)).thenReturn(mapped);


        Optional<System> result = underTest.load(name);


        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(mapped));
    }
}