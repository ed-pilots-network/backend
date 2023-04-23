package io.edpn.edpnbackend.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.edpnbackend.application.service.GetStationService;
import io.edpn.edpnbackend.application.usecase.GetStationUsecase;
import io.edpn.edpnbackend.infrastructure.kafka.KafkaTopicHandler;
import io.edpn.edpnbackend.infrastructure.zmq.EddnMessageHandler;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class BeanConfig {
    @Bean
    public GetStationService getStationService() {
        return new GetStationService();
    }

    @Bean
    public GetStationUsecase getStationUsecase(GetStationService getStationService) {
        return getStationService;
    }

    @Bean
    public EddnMessageHandler eddnMessageHandler(@Qualifier("eddnTaskExecutor") TaskExecutor taskExecutor,
                                                 @Qualifier("eddnRetryTemplate") RetryTemplate retryTemplate,
                                                 ObjectMapper objectMapper,
                                                 KafkaTopicHandler kafkaTopicHandler,
                                                 KafkaTemplate<String, JsonNode> jsonNodekafkaTemplate) {
        return new EddnMessageHandler(taskExecutor, retryTemplate, objectMapper, kafkaTopicHandler, jsonNodekafkaTemplate);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
