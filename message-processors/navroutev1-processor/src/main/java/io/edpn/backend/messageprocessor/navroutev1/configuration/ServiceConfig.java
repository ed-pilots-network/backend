package io.edpn.backend.messageprocessor.navroutev1.configuration;

import io.edpn.backend.messageprocessor.navroutev1.application.service.ReceiveNavRouteMessageService;
import io.edpn.backend.messageprocessor.navroutev1.application.usecase.ReceiveNavRouteMessageUseCase;
import io.edpn.backend.messageprocessor.navroutev1.domain.repository.SystemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public ReceiveNavRouteMessageUseCase receiveNavRouteMessageUseCase(SystemRepository systemRepository) {
        return new ReceiveNavRouteMessageService(systemRepository);
    }
}
