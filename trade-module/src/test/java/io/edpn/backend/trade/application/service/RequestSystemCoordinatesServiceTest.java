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
public class RequestSystemCoordinatesServiceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private RequestDataMessageRepository requestDataMessageRepository;
    private RequestDataService<System> underTest;

    public static Stream<Arguments> provideDoublesForCheckApplicability() {
        return Stream.of(
                Arguments.of(null, null, null, true),
                Arguments.of(0.0, null, null, true),
                Arguments.of(null, 0.0, null, true),
                Arguments.of(null, null, 0.0, true),
                Arguments.of(0.0, 0.0, 0.0, false),
                Arguments.of(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, false)
        );
    }

    @BeforeEach
    void setUp() {
        underTest = new RequestSystemCoordinatesService(requestDataMessageRepository, objectMapper);
    }

    @ParameterizedTest
    @MethodSource("provideDoublesForCheckApplicability")
    void shouldCheckApplicability(Double xCoordinate, Double yCoordinate, Double zCoordinate, boolean expected) {
        System systemWithCoordinates = System.builder()
                .xCoordinate(xCoordinate)
                .yCoordinate(yCoordinate)
                .zCoordinate(zCoordinate)
                .build();

        assertThat(underTest.isApplicable(systemWithCoordinates), is(expected));
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
        assertThat(message.getTopic(), is("systemCoordinatesDataRequest"));
        assertThat(message.getMessage(), is(notNullValue()));

        SystemDataRequest actualSystemDataRequest = objectMapper.treeToValue(message.getMessage(), SystemDataRequest.class);
        assertThat(actualSystemDataRequest.getSystemName(), is(system.getName()));
    }
}
