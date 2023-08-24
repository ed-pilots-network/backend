package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.dto.web.object.mapper.MessageMapper;
import io.edpn.backend.trade.application.port.incomming.kafka.RequestDataUseCase;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.util.Topic;
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
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RequestSystemEliteIdServiceTest {
    
    @Mock
    private SendKafkaMessagePort sendKafkaMessagePort;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Mock
    private MessageMapper messageMapper;
    
    private RequestDataUseCase<System> underTest;

    public static Stream<Arguments> provideLongForCheckApplicability() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of(0L, false),
                Arguments.of(Long.MAX_VALUE, false)
        );
    }

    @BeforeEach
    void setUp() {
        underTest = new RequestSystemEliteIdService(sendKafkaMessagePort, objectMapper, messageMapper);
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
        
        ArgumentCaptor<Message> argumentCaptor = ArgumentCaptor.forClass(Message.class);
        verify(messageMapper, times(1)).map(argumentCaptor.capture());
        verify(sendKafkaMessagePort, times(1)).send(messageMapper.map(argumentCaptor.capture()));
        
        Message message = argumentCaptor.getValue();
        assertThat(message, is(notNullValue()));
        assertThat(message.getTopic(), is(Topic.Request.SYSTEM_ELITE_ID.getTopicName()));
        assertThat(message.getMessage(), is(notNullValue()));
        
        //TODO: below
        //SystemDataRequest actualSystemDataRequest = objectMapper.treeToValue(message.getMessage(), SystemDataRequest.class);
        assertThat(message.getMessage(), containsString(system.getName()));
    }
}
