package io.edpn.backend.exploration.adapter.kafka.dto.mapper;

import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemEliteIdResponseMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemEliteIdResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class KafkaSystemEliteIdResponseMapperTest {

    private SystemEliteIdResponseMapper underTest;

    @BeforeEach
    void setUp() {
        underTest = new KafkaSystemEliteIdResponseMapper();
    }

    @Test
    void map_shouldReturnCorrectSystemEliteIdResponse() {

        String systemName = "test-system";
        Long eliteId = 1L;
        System system = new System(UUID.randomUUID(), eliteId, systemName, null, null);


        SystemEliteIdResponse result = underTest.map(system);


        assertThat(result.systemName(), is(systemName));
        assertThat(result.eliteId(), is(eliteId));
    }
}
