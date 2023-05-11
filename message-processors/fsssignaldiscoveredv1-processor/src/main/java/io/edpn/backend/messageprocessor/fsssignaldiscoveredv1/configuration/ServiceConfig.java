package io.edpn.backend.messageprocessor.fsssignaldiscoveredv1.configuration;

import io.edpn.backend.messageprocessor.fsssignaldiscoveredv1.application.service.ReceiveFssSignalDiscoveredMessageService;
import io.edpn.backend.messageprocessor.fsssignaldiscoveredv1.application.usecase.ReceiveFssSignalDiscoveredMessageUseCase;
import io.edpn.backend.messageprocessor.fsssignaldiscoveredv1.domain.repository.SystemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public ReceiveFssSignalDiscoveredMessageUseCase receiveFssSignalDiscoveredMessageUseCase(SystemRepository systemRepository) {
        return new ReceiveFssSignalDiscoveredMessageService(systemRepository);
    }
}
