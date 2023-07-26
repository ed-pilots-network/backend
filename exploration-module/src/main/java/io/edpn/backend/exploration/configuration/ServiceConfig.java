package io.edpn.backend.exploration.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.service.DefaultSendSystemCoordinatesResponseService;
import io.edpn.backend.exploration.domain.model.RequestDataMessage;
import io.edpn.backend.exploration.domain.repository.RequestDataMessageRepository;
import io.edpn.backend.exploration.domain.service.KafkaSenderService;
import io.edpn.backend.exploration.domain.service.SendDataResponseService;
import io.edpn.backend.exploration.infrastructure.kafka.KafkaTopicHandler;
import io.edpn.backend.exploration.infrastructure.kafka.sender.RequestDataMessageKafkaSenderService;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration("ExplorationModuleServiceConfig")
public class ServiceConfig {

    @Bean(name = "explorationRequestDataMessageKafkaSender")
    public KafkaSenderService<RequestDataMessage> requestDataMessageKafkaSenderService(ObjectMapper objectMapper, RequestDataMessageRepository requestDataMessageRepository, KafkaTopicHandler kafkaTopicHandler, @Qualifier("tradeJsonNodekafkaTemplate") KafkaTemplate<String, JsonNode> jsonNodekafkaTemplate) {
        return new RequestDataMessageKafkaSenderService(objectMapper, requestDataMessageRepository, kafkaTopicHandler, jsonNodekafkaTemplate);
    }

    @Bean(name = "explorationSendSystemCoordinatesResponseService")
    public SendDataResponseService<SystemCoordinatesResponse> sendSystemCoordinatesResponseService(KafkaSenderService<RequestDataMessage> kafkaSenderService, ObjectMapper objectMapper) {
        return new DefaultSendSystemCoordinatesResponseService(kafkaSenderService, objectMapper);
    }
}
