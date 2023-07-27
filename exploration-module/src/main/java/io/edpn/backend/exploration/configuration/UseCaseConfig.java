package io.edpn.backend.exploration.configuration;

import io.edpn.backend.exploration.application.mappers.v1.SystemDtoMapper;
import io.edpn.backend.exploration.application.usecase.DefaultFindSystemsFromSearchbarUseCase;
import io.edpn.backend.exploration.application.usecase.DefaultReceiveNavRouteMessageUseCase;
import io.edpn.backend.exploration.application.usecase.DefaultReceiveSystemCoordinatesRequestUseCase;
import io.edpn.backend.exploration.domain.usecase.FindSystemsFromSearchbarUseCase;
import io.edpn.backend.exploration.domain.usecase.ReceiveDataRequestUseCase;
import io.edpn.backend.exploration.domain.usecase.ReceiveNavRouteMessageUseCase;
import io.edpn.backend.exploration.domain.repository.SystemCoordinateDataRequestRepository;
import io.edpn.backend.exploration.domain.repository.SystemRepository;
import io.edpn.backend.exploration.domain.service.SendDataResponseService;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("ExplorationModuleUseCaseConfig")
public class UseCaseConfig {

    @Bean(name = "explorationReceiveSystemCoordinatesRequestUseCase")
    public ReceiveDataRequestUseCase<SystemDataRequest> receiveSystemCoordinatesRequestUseCase(SystemRepository systemRepository,
                                                                                               SystemCoordinateDataRequestRepository systemCoordinateDataRequestRepository,
                                                                                               SendDataResponseService<SystemCoordinatesResponse> sendSystemCoordinateDataResponseService) {
        return new DefaultReceiveSystemCoordinatesRequestUseCase(systemRepository, systemCoordinateDataRequestRepository, sendSystemCoordinateDataResponseService);
    }

    @Bean(name = "explorationReceiveNavRouteMessageUseCase")
    public ReceiveNavRouteMessageUseCase receiveNavRouteMessageUseCase(SystemRepository systemRepository,
                                                                       SystemCoordinateDataRequestRepository systemCoordinateDataRequestRepository,
                                                                       SendDataResponseService<SystemCoordinatesResponse> SendSystemCoordinateDataResponseService) {
        return new DefaultReceiveNavRouteMessageUseCase(systemRepository, systemCoordinateDataRequestRepository, SendSystemCoordinateDataResponseService);
    }

    @Bean(name = "explorationFindSystemsFromSearchbarUseCase")
    public FindSystemsFromSearchbarUseCase findSystemsFromSearchbarUseCase(SystemRepository systemRepository, SystemDtoMapper systemDtoMapper) {
        return new DefaultFindSystemsFromSearchbarUseCase(systemRepository, systemDtoMapper);
    }
}
