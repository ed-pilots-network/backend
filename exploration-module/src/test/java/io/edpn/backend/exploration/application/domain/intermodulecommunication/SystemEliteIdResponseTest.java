package io.edpn.backend.exploration.application.domain.intermodulecommunication;

import io.edpn.backend.exploration.application.domain.System;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class SystemEliteIdResponseTest {

    @Test
    void map_shouldReturnCorrectSystemEliteIdResponse() {

        String systemName = "test-system";
        Long eliteId = 1L;
        System system = new System(UUID.randomUUID(), eliteId, systemName, null, null);


        SystemEliteIdResponse result = SystemEliteIdResponse.from(system);


        assertThat(result.systemName(), is(systemName));
        assertThat(result.eliteId(), is(eliteId));
    }
}
