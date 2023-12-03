package io.edpn.backend.exploration.adapter.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.journal.DockedMessage;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JournalV1DockedMessageProcessorTest {

    @Mock
    private ReceiveKafkaMessageUseCase<DockedMessage.V1> receiveKafkaMessageUseCase;

    @Mock
    private ObjectMapper objectMapper;

    private MessageProcessor<DockedMessage.V1> underTest;

    @BeforeEach
    void setUp() {
        underTest = new JournalDockedV1MessageProcessor(receiveKafkaMessageUseCase, objectMapper);
    }

    @Test
    void listen_shouldInvokeUseCaseWithCorrectNavRouteMessage() throws JsonProcessingException {

        JsonNode jsonNode = mock(JsonNode.class);
        DockedMessage.V1 navRouteMessage = mock(DockedMessage.V1.class);

        Mockito.when(objectMapper.treeToValue(jsonNode, DockedMessage.V1.class)).thenReturn(navRouteMessage);


        underTest.listen(jsonNode);


        verify(receiveKafkaMessageUseCase, times(1)).receive(any(DockedMessage.V1.class));
    }

}