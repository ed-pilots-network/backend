package io.edpn.backend.exploration.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.domain.usecase.ReceiveDataRequestUseCase;
import io.edpn.backend.exploration.domain.usecase.ReceiveNavRouteMessageUseCase;
import io.edpn.backend.exploration.infrastructure.kafka.processor.NavRouteV1MessageProcessor;
import io.edpn.backend.exploration.infrastructure.kafka.processor.SystemCoordinatesRequestMessageProcessor;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.NavRouteMessage;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("ExplorationModuleMessagingConfig")
public class MessagingConfig {


    @Bean(name = "explorationNavRouteV1MessageProcessor")
    public MessageProcessor<NavRouteMessage.V1> navRouteV1MessageProcessor(ReceiveNavRouteMessageUseCase receiveNavRouteMessageUseCase, ObjectMapper objectMapper) {
        return new NavRouteV1MessageProcessor(receiveNavRouteMessageUseCase, objectMapper);
    }

    @Bean(name = "explorationSystemCoordinatesRequestMessageProcessor")
    public MessageProcessor<SystemDataRequest> systemCoordinatesRequestMessageProcessor(ReceiveDataRequestUseCase<SystemDataRequest> receiveDataRequestUseCase, ObjectMapper objectMapper) {
        return new SystemCoordinatesRequestMessageProcessor(receiveDataRequestUseCase, objectMapper);
    }
}