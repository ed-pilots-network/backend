package io.edpn.backend.exploration.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.adapter.kafka.dto.SystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.adapter.persistence.SystemRepository;
import io.edpn.backend.exploration.adapter.web.dto.SystemDtoMapper;
import io.edpn.backend.exploration.application.port.outgoing.CreateSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.CreateSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadAllSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemCoordinateRequestBySystemNamePort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.SaveSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.SendKafkaMessagePort;
import io.edpn.backend.exploration.application.service.ProcessPendingSystemCoordinateRequestService;
import io.edpn.backend.exploration.application.service.ReceiveNavRouteService;
import io.edpn.backend.exploration.application.service.ReceiveSystemCoordinateRequestService;
import io.edpn.backend.exploration.application.service.SystemControllerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

import java.util.concurrent.Executor;

@Configuration("ExplorationServiceConfig")
public class ServiceConfig {

    @Bean(name = "explorationReceiveNavRouteService")
    public ReceiveNavRouteService receiveNavRouteService(
            CreateSystemPort createSystemPort,
            LoadSystemPort loadSystemPort,
            SaveSystemPort saveSystemPort,
            SendKafkaMessagePort sendKafkaMessagePort,
            LoadSystemCoordinateRequestBySystemNamePort loadSystemCoordinateRequestBySystemNamePort,
            DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort,
            SystemCoordinatesResponseMapper systemCoordinatesResponseMapper,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper,
            @Qualifier("explorationRetryTemplate") RetryTemplate retryTemplate
    ) {
        return new ReceiveNavRouteService(createSystemPort, loadSystemPort, saveSystemPort, sendKafkaMessagePort, loadSystemCoordinateRequestBySystemNamePort, deleteSystemCoordinateRequestPort, systemCoordinatesResponseMapper, objectMapper, retryTemplate);
    }

    @Bean(name = "explorationReceiveSystemCoordinateRequestService")
    public ReceiveSystemCoordinateRequestService receiveSystemCoordinateRequestService(
            CreateSystemCoordinateRequestPort createSystemCoordinateRequestPort,
            LoadSystemPort loadSystemPort,
            SendKafkaMessagePort sendKafkaMessagePort,
            SystemCoordinatesResponseMapper systemCoordinatesResponseMapper,
            ObjectMapper objectMapper,
            @Qualifier("explorationRetryTemplate") RetryTemplate retryTemplate
    ) {
        return new ReceiveSystemCoordinateRequestService(createSystemCoordinateRequestPort, loadSystemPort, sendKafkaMessagePort, systemCoordinatesResponseMapper, objectMapper, retryTemplate);
    }

    @Bean(name = "explorationSystemControllerService")
    public SystemControllerService systemControllerService(
            SystemRepository systemRepository,
            SystemDtoMapper systemDtoMapper) {
        return new SystemControllerService(systemRepository, systemDtoMapper);
    }

    @Bean(name = "explorationProcessPendingSystemCoordinateRequestService")
    public ProcessPendingSystemCoordinateRequestService processPendingSystemCoordinateRequestService(
            LoadAllSystemCoordinateRequestPort loadAllSystemCoordinateRequestPort,
            LoadSystemPort loadSystemPort,
            SendKafkaMessagePort sendKafkaMessagePort,
            DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort,
            SystemCoordinatesResponseMapper systemCoordinatesResponseMapper,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper,
            @Qualifier("explorationRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("explorationThreadPoolTaskExecutor") Executor executor
    ) {
        return new ProcessPendingSystemCoordinateRequestService(loadAllSystemCoordinateRequestPort, loadSystemPort, sendKafkaMessagePort, deleteSystemCoordinateRequestPort, systemCoordinatesResponseMapper, objectMapper, retryTemplate, executor);
    }
}
