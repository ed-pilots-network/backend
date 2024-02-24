package io.edpn.backend.exploration.adapter.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.adapter.kafka.processor.JournalDockedV1MessageProcessor;
import io.edpn.backend.exploration.adapter.kafka.processor.JournalScanV1MessageProcessor;
import io.edpn.backend.exploration.adapter.kafka.processor.NavRouteV1MessageProcessor;
import io.edpn.backend.exploration.adapter.kafka.processor.StationDataRequestMessageProcessor;
import io.edpn.backend.exploration.adapter.kafka.processor.SystemDataRequestMessageProcessor;
import io.edpn.backend.exploration.adapter.kafka.sender.KafkaMessageSender;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.topic.CreateTopicPort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.NavRouteMessage;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.journal.DockedMessage;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.journal.ScanMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration("ExplorationModuleMessagingConfig")
public class MessageProcessorConfig {


    @Bean(name = "explorationNavRouteV1MessageProcessor")
    public NavRouteV1MessageProcessor navRouteV1MessageProcessor(
            ReceiveKafkaMessageUseCase<NavRouteMessage.V1> receiveNavRouteMessageUseCase,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper
    ) {
        return new NavRouteV1MessageProcessor(receiveNavRouteMessageUseCase, objectMapper);
    }
    
    @Bean(name = "explorationJournalScanV1MessageProcessor")
    public JournalScanV1MessageProcessor journalScanV1MessageProcessor(
            ReceiveKafkaMessageUseCase<ScanMessage.V1> receiveJournalScanMessageUseCase,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper
    ) {
        return new JournalScanV1MessageProcessor(receiveJournalScanMessageUseCase, objectMapper);
    }

    @Bean(name = "explorationJournalDockedV1MessageProcessor")
    public JournalDockedV1MessageProcessor journalDockedV1MessageProcessor(
            ReceiveKafkaMessageUseCase<DockedMessage.V1> receiveJournalDockedMessageUseCase,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper
    ) {
        return new JournalDockedV1MessageProcessor(receiveJournalDockedMessageUseCase, objectMapper);
    }

    @Bean(name = "explorationSystemCoordinatesRequestMessageProcessor")
    public SystemDataRequestMessageProcessor systemCoordinatesRequestMessageProcessor(
            @Qualifier("explorationSystemCoordinateInterModuleCommunicationService") ReceiveKafkaMessageUseCase<SystemDataRequest> receiveSystemDataRequestUseCase,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper
    ) {
        return new SystemDataRequestMessageProcessor(receiveSystemDataRequestUseCase, objectMapper);
    }

    @Bean(name = "explorationSystemEliteIdRequestMessageProcessor")
    public SystemDataRequestMessageProcessor systemEliteIdRequestMessageProcessor(
            @Qualifier("explorationSystemEliteIdInterModuleCommunicationService") ReceiveKafkaMessageUseCase<SystemDataRequest> receiveSystemDataRequestUseCase,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper
    ) {
        return new SystemDataRequestMessageProcessor(receiveSystemDataRequestUseCase, objectMapper);
    }

    @Bean(name = "explorationStationMaxLandingPadSizeRequestMessageProcessor")
    public StationDataRequestMessageProcessor stationMaxLandingPadSizerRequestMessageProcessor(
            @Qualifier("explorationStationMaxLandingPadSizeInterModuleCommunicationService") ReceiveKafkaMessageUseCase<StationDataRequest> receiveStationDataRequestUseCase,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper
    ) {
        return new StationDataRequestMessageProcessor(receiveStationDataRequestUseCase, objectMapper);
    }

    @Bean(name = "explorationKafkaMessageSender")
    public KafkaMessageSender kafkaMessageSender(
            CreateTopicPort createTopicPort,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper,
            @Qualifier("explorationJsonNodekafkaTemplate") KafkaTemplate<String, JsonNode> jsonNodekafkaTemplate
    ) {
        return new KafkaMessageSender(createTopicPort, objectMapper, jsonNodekafkaTemplate);
    }
}
