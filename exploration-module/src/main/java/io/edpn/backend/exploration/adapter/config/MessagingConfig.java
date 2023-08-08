package io.edpn.backend.exploration.adapter.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.adapter.kafka.processor.NavRouteV1MessageProcessor;
import io.edpn.backend.exploration.adapter.kafka.processor.SystemCoordinatesRequestMessageProcessor;
import io.edpn.backend.exploration.adapter.kafka.sender.KafkaMessageSender;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.topic.CreateTopicPort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.NavRouteMessage;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration("ExplorationModuleMessagingConfig")
public class MessagingConfig {


    @Bean(name = "explorationNavRouteV1MessageProcessor")
    public NavRouteV1MessageProcessor navRouteV1MessageProcessor(
            ReceiveKafkaMessageUseCase<NavRouteMessage.V1> receiveNavRouteMessageUseCase,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper
    ) {
        return new NavRouteV1MessageProcessor(receiveNavRouteMessageUseCase, objectMapper);
    }

    @Bean(name = "explorationSystemCoordinatesRequestMessageProcessor")
    public SystemCoordinatesRequestMessageProcessor systemCoordinatesRequestMessageProcessor(
            ReceiveKafkaMessageUseCase<SystemDataRequest> receiveSystemDataRequestUseCase,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper
    ) {
        return new SystemCoordinatesRequestMessageProcessor(receiveSystemDataRequestUseCase, objectMapper);
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
