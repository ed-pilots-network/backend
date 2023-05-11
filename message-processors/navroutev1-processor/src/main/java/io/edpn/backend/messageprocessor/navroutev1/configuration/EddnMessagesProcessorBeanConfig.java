package io.edpn.backend.messageprocessor.navroutev1.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessor.navroutev1.application.usecase.ReceiveNavRouteMessageUseCase;
import io.edpn.backend.messageprocessor.navroutev1.infrastructure.kafka.processor.NavRouteMessageProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EddnMessagesProcessorBeanConfig {
    @Bean
    public NavRouteMessageProcessor navRouteMessageProcessor(ReceiveNavRouteMessageUseCase receiveNavRouteMessageUsecase, ObjectMapper objectMapper) {
        return new NavRouteMessageProcessor(receiveNavRouteMessageUsecase, objectMapper);
    }
}
