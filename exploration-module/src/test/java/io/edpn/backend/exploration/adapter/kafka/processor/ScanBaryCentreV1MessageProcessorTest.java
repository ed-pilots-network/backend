package io.edpn.backend.exploration.adapter.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.journal.ScanMessage;
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
class ScanBaryCentreV1MessageProcessorTest {

    @Mock
    private ReceiveKafkaMessageUseCase<ScanMessage.V1> receiveKafkaMessageUseCase;

    @Mock
    private ObjectMapper objectMapper;

    private MessageProcessor<ScanMessage.V1> underTest;

    @BeforeEach
    void setUp() {
        underTest = new JournalScanV1MessageProcessor(receiveKafkaMessageUseCase, objectMapper);
    }

    @Test
    void listen_shouldInvokeUseCaseWithCorrectScanBaryCentreMessage() throws JsonProcessingException {

        JsonNode jsonNode = mock(JsonNode.class);
        ScanMessage.V1 journalScanMessage = mock(ScanMessage.V1.class);

        Mockito.when(objectMapper.treeToValue(jsonNode, ScanMessage.V1.class)).thenReturn(journalScanMessage);


        underTest.listen(jsonNode);


        verify(receiveKafkaMessageUseCase, times(1)).receive(any(ScanMessage.V1.class));
    }

}