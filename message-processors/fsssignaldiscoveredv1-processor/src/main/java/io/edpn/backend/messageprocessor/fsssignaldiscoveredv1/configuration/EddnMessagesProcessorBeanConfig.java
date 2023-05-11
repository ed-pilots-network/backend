package io.edpn.backend.messageprocessor.fsssignaldiscoveredv1.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessor.fsssignaldiscoveredv1.application.usecase.ReceiveFssSignalDiscoveredMessageUseCase;
import io.edpn.backend.messageprocessor.fsssignaldiscoveredv1.infrastructure.kafka.processor.FssSignalDiscoveredMessageProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EddnMessagesProcessorBeanConfig {
    @Bean
    public FssSignalDiscoveredMessageProcessor fssSignalDiscoveredMessageProcessor(ReceiveFssSignalDiscoveredMessageUseCase receiveFssSignalDiscoveredMessageUseCase, ObjectMapper objectMapper) {
        return new FssSignalDiscoveredMessageProcessor(receiveFssSignalDiscoveredMessageUseCase, objectMapper);
    }
}
