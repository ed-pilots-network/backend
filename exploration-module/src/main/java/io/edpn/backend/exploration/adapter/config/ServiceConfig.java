package io.edpn.backend.exploration.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.adapter.kafka.dto.mapper.KafkaSystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.adapter.persistence.SystemRepository;
import io.edpn.backend.exploration.adapter.web.dto.mapper.RestSystemDtoMapper;
import io.edpn.backend.exploration.application.dto.mapper.KafkaMessageMapper;
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
            KafkaSystemCoordinatesResponseMapper kafkaSystemCoordinatesResponseMapper,
            KafkaMessageMapper kafkaMessageMapper,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper,
            @Qualifier("explorationRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("explorationThreadPoolTaskExecutor") Executor executor
    ) {
        return new ReceiveNavRouteService(createSystemPort, loadSystemPort, saveSystemPort, sendKafkaMessagePort, loadSystemCoordinateRequestBySystemNamePort, deleteSystemCoordinateRequestPort, kafkaSystemCoordinatesResponseMapper, kafkaMessageMapper, objectMapper, retryTemplate, executor);
    }

    @Bean(name = "explorationReceiveSystemCoordinateRequestService")
    public ReceiveSystemCoordinateRequestService receiveSystemCoordinateRequestService(
            CreateSystemCoordinateRequestPort createSystemCoordinateRequestPort,
            LoadSystemPort loadSystemPort,
            SendKafkaMessagePort sendKafkaMessagePort,
            KafkaSystemCoordinatesResponseMapper kafkaSystemCoordinatesResponseMapper,
            KafkaMessageMapper kafkaMessageMapper,
            ObjectMapper objectMapper,
            @Qualifier("explorationRetryTemplate") RetryTemplate retryTemplate
    ) {
        return new ReceiveSystemCoordinateRequestService(createSystemCoordinateRequestPort, loadSystemPort, sendKafkaMessagePort, kafkaSystemCoordinatesResponseMapper, kafkaMessageMapper, objectMapper, retryTemplate);
    }

    @Bean(name = "explorationSystemControllerService")
    public SystemControllerService systemControllerService(
            SystemRepository systemRepository,
            RestSystemDtoMapper restSystemDtoMapper) {
        return new SystemControllerService(systemRepository, restSystemDtoMapper);
    }

    @Bean(name = "explorationProcessPendingSystemCoordinateRequestService")
    public ProcessPendingSystemCoordinateRequestService processPendingSystemCoordinateRequestService(
            LoadAllSystemCoordinateRequestPort loadAllSystemCoordinateRequestPort,
            LoadSystemPort loadSystemPort,
            SendKafkaMessagePort sendKafkaMessagePort,
            DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort,
            KafkaSystemCoordinatesResponseMapper kafkaSystemCoordinatesResponseMapper,
            KafkaMessageMapper kafkaMessageMapper,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper,
            @Qualifier("explorationRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("explorationThreadPoolTaskExecutor") Executor executor
    ) {
        return new ProcessPendingSystemCoordinateRequestService(loadAllSystemCoordinateRequestPort, loadSystemPort, sendKafkaMessagePort, deleteSystemCoordinateRequestPort, kafkaSystemCoordinatesResponseMapper, kafkaMessageMapper, objectMapper, retryTemplate, executor);
    }
}
