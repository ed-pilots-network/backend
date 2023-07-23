package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.domain.model.RequestDataMessage;
import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.domain.repository.RequestDataMessageRepository;
import io.edpn.backend.trade.domain.service.RequestDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RequestSystemEliteIdServiceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private RequestDataMessageRepository requestDataMessageRepository;
    private RequestDataService<System> underTest;

    public static Stream<Arguments> provideLongForCheckApplicability() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of(0L, false),
                Arguments.of(Long.MAX_VALUE, false)
        );
    }

    @BeforeEach
    void setUp() {
        underTest = new RequestSystemEliteIdService(requestDataMessageRepository, objectMapper);
    }

    @ParameterizedTest
    @MethodSource("provideLongForCheckApplicability")
    void shouldCheckApplicability(Long eliteId, boolean expected) {
        System systemWithEliteId = System.builder()
                .eliteId(eliteId)
                .build();

        assertThat(underTest.isApplicable(systemWithEliteId), is(expected));
    }

    @Test
    void shouldSendRequest() throws Exception {
        System system = System.builder()
                .name("Test System")
                .build();

        underTest.request(system);

        ArgumentCaptor<RequestDataMessage> argumentCaptor = ArgumentCaptor.forClass(RequestDataMessage.class);
        verify(requestDataMessageRepository, times(1)).sendToKafka(argumentCaptor.capture());

        RequestDataMessage message = argumentCaptor.getValue();
        assertThat(message, is(notNullValue()));
        assertThat(message.getTopic(), is("systemEliteIdDataRequest"));
        assertThat(message.getMessage(), is(notNullValue()));

        SystemDataRequest actualSystemDataRequest = objectMapper.treeToValue(message.getMessage(), SystemDataRequest.class);
        assertThat(actualSystemDataRequest.getSystemName(), is(system.getName()));
    }
}
