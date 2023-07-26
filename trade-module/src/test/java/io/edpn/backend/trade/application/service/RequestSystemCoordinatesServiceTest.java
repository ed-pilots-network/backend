package io.edpn.backend.trade.application.service;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.domain.service.RequestDataService;
import io.edpn.backend.trade.domain.service.SendDataRequestService;
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RequestSystemCoordinatesServiceTest {

    @Mock
    private SendDataRequestService<SystemDataRequest> systemDataRequestSendDataRequestService;
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
        underTest = new RequestSystemCoordinatesService(systemDataRequestSendDataRequestService);
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
    void shouldSendRequest() {
        System system = System.builder()
                .name("Test System")
                .build();

        underTest.request(system);

        ArgumentCaptor<SystemDataRequest> systemDataRequestArgumentCaptor = ArgumentCaptor.forClass(SystemDataRequest.class);
        ArgumentCaptor<String> topicArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(systemDataRequestSendDataRequestService, times(1)).send(systemDataRequestArgumentCaptor.capture(), topicArgumentCaptor.capture());

        SystemDataRequest actualSystemDataRequest = systemDataRequestArgumentCaptor.getValue();
        String actualTopic = topicArgumentCaptor.getValue();
        assertThat(actualSystemDataRequest.getSystemName(), is(system.getName()));
        assertThat(actualSystemDataRequest.getRequestingModule(), is("trade"));
        assertThat(actualTopic, equalTo("systemCoordinatesDataRequest"));
    }
}
