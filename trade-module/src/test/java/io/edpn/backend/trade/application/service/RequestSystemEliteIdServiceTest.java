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
public class RequestSystemEliteIdServiceTest {

    @Mock
    private SendDataRequestService<SystemDataRequest> systemDataRequestSendDataRequestService;
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
        underTest = new RequestSystemEliteIdService(systemDataRequestSendDataRequestService);
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
        assertThat(actualTopic, equalTo("systemEliteIdDataRequest"));
    }
}
