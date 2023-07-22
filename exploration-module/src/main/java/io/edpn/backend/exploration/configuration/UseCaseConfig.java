package io.edpn.backend.exploration.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.usecase.DefaultReceiveNavRouteMessageUseCase;
import io.edpn.backend.exploration.application.usecase.DefaultReceiveSystemCoordinatesRequestUseCase;
import io.edpn.backend.exploration.domain.repository.RequestDataMessageRepository;
import io.edpn.backend.exploration.domain.repository.SystemCoordinateDataRequestRepository;
import io.edpn.backend.exploration.domain.repository.SystemRepository;
import io.edpn.backend.exploration.domain.usecase.ReceiveDataRequestUseCase;
import io.edpn.backend.exploration.domain.usecase.ReceiveNavRouteMessageUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("ExplorationModuleUseCaseConfig")
public class UseCaseConfig {

    @Bean(name = "explorationReceiveSystemCoordinatesRequestUseCase")
    public ReceiveDataRequestUseCase<SystemDataRequest> receiveSystemCoordinatesRequestUseCase(SystemRepository systemRepository,
                                                                                               RequestDataMessageRepository requestDataMessageRepository,
                                                                                               SystemCoordinateDataRequestRepository systemCoordinateDataRequestRepository,
                                                                                               ObjectMapper objectMapper) {
        return new DefaultReceiveSystemCoordinatesRequestUseCase(systemRepository, requestDataMessageRepository, systemCoordinateDataRequestRepository, objectMapper);
    }

    @Bean(name = "explorationReceiveNavRouteMessageUseCase")
    public ReceiveNavRouteMessageUseCase receiveNavRouteMessageUseCase(SystemRepository systemRepository,
                                                                                SystemCoordinateDataRequestRepository systemCoordinateDataRequestRepository,
                                                                                RequestDataMessageRepository requestDataMessageRepository,
                                                                                ObjectMapper objectMapper) {
        return new DefaultReceiveNavRouteMessageUseCase(systemRepository, systemCoordinateDataRequestRepository, requestDataMessageRepository, objectMapper);
    }
}
