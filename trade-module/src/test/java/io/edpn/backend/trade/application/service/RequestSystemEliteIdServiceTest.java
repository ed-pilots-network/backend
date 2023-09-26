package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.dto.web.object.MessageDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.MessageMapper;
import io.edpn.backend.trade.application.port.incomming.kafka.RequestDataUseCase;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadOrCreateSystemByNamePort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.system.UpdateSystemPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.CreateSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.ExistsSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.LoadAllSystemEliteIdRequestsPort;
import io.edpn.backend.util.Module;
import io.edpn.backend.util.Topic;
import java.util.concurrent.Executor;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.retry.support.RetryTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestSystemEliteIdServiceTest {

    @Mock
    private LoadSystemsByFilterPort loadSystemsByFilterPort;
    @Mock
    private LoadAllSystemEliteIdRequestsPort loadAllSystemEliteIdRequestsPort;
    @Mock
    private LoadOrCreateSystemByNamePort loadOrCreateSystemByNamePort;
    @Mock
    private ExistsSystemEliteIdRequestPort existsSystemEliteIdRequestPort;
    @Mock
    private CreateSystemEliteIdRequestPort createSystemEliteIdRequestPort;
    @Mock
    private DeleteSystemEliteIdRequestPort deleteSystemEliteIdRequestPort;
    @Mock
    private UpdateSystemPort updateSystemPort;
    @Mock
    private SendKafkaMessagePort sendKafkaMessagePort;
    @Mock
    private RetryTemplate retryTemplate;
    @Mock
    private Executor executor;
    @Mock
    private ObjectMapper objectMapper;
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
        underTest = new SystemEliteIdInterModuleCommunicationService(
                loadSystemsByFilterPort,
                loadAllSystemEliteIdRequestsPort,
                loadOrCreateSystemByNamePort,
                existsSystemEliteIdRequestPort,
                createSystemEliteIdRequestPort,
                deleteSystemEliteIdRequestPort,
                updateSystemPort,
                sendKafkaMessagePort,
                retryTemplate,
                executor,
                objectMapper,
                messageMapper
        );
    }

    @ParameterizedTest
    @MethodSource("provideLongForCheckApplicability")
    void shouldCheckApplicability(Long eliteId, boolean expected) {
        System systemWithEliteId = System.builder()
                .eliteId(eliteId)
                .build();

        assertThat(underTest.isApplicable(systemWithEliteId), is(expected));
    }

    /*@Test
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
    }*/

    @Test
    public void testRequestWhenIdExists() {
        String systemName = "Test System";
        System system = System.builder()
                .name(systemName)
                .build();

        when(existsSystemEliteIdRequestPort.exists(systemName)).thenReturn(true);

        underTest.request(system);

        verify(sendKafkaMessagePort, never()).send(any());
        verify(createSystemEliteIdRequestPort, never()).create(anyString());
    }

    @Test
    public void testRequestWhenIdDoesNotExist() {
        String systemName = "Test System";
        System system = System.builder()
                .name(systemName)
                .build();

        JsonNode mockJsonNode = mock(JsonNode.class);
        String mockJsonString = "jsonString";
        MessageDto mockMessageDto = mock(MessageDto.class);

        when(existsSystemEliteIdRequestPort.exists(systemName)).thenReturn(false);
        when(objectMapper.valueToTree(argThat(arg -> {
            if (arg instanceof SystemDataRequest systemDataRequest) {
                return systemName.equals(systemDataRequest.systemName()) && Module.TRADE.equals(systemDataRequest.requestingModule());
            } else {
                return false;
            }
        }))).thenReturn(mockJsonNode);
        when(mockJsonNode.toString()).thenReturn(mockJsonString);
        when(messageMapper.map(argThat(argument ->
                argument.getMessage().equals(mockJsonString) && argument.getTopic().equals(Topic.Request.SYSTEM_ELITE_ID.getTopicName())
        ))).thenReturn(mockMessageDto);

        ArgumentCaptor<Message> argumentCaptor = ArgumentCaptor.forClass(Message.class);


        underTest.request(system);

        verify(sendKafkaMessagePort).send(mockMessageDto);
        verify(createSystemEliteIdRequestPort).create(systemName);
        verify(messageMapper, times(1)).map(argumentCaptor.capture());
        Message message = argumentCaptor.getValue();
        assertThat(message, is(notNullValue()));
        assertThat(message.getTopic(), is(Topic.Request.SYSTEM_ELITE_ID.getTopicName()));
        assertThat(message.getMessage(), is("jsonString"));
    }
}
