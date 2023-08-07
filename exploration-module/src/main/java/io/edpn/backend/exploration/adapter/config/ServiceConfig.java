package io.edpn.backend.exploration.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.adapter.kafka.dto.mapper.KafkaSystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.adapter.persistence.SystemRepository;
import io.edpn.backend.exploration.adapter.web.dto.mapper.RestSystemDtoMapper;
import io.edpn.backend.exploration.application.dto.mapper.MessageMapper;
import io.edpn.backend.exploration.application.port.outgoing.CreateSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.CreateSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadAllSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemCoordinateRequestBySystemNamePort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.SaveSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.SendKafkaMessagePort;
import io.edpn.backend.exploration.application.service.ProcessPendingSystemCoordinateRequestService;
import io.edpn.backend.exploration.application.service.ReceiveNavRouteService;
import io.edpn.backend.exploration.application.service.ReceiveSystemCoordinateRequestService;
import io.edpn.backend.exploration.application.service.SystemControllerService;
import io.edpn.backend.exploration.application.validation.LoadByNameContainingValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
            MessageMapper messageMapper,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper,
            @Qualifier("explorationRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("explorationThreadPoolTaskExecutor") Executor executor
    ) {
        return new ReceiveNavRouteService(createSystemPort, loadSystemPort, saveSystemPort, sendKafkaMessagePort, loadSystemCoordinateRequestBySystemNamePort, deleteSystemCoordinateRequestPort, kafkaSystemCoordinatesResponseMapper, messageMapper, objectMapper, retryTemplate, executor);
    }

    @Bean(name = "explorationReceiveSystemCoordinateRequestService")
    public ReceiveSystemCoordinateRequestService receiveSystemCoordinateRequestService(
            CreateSystemCoordinateRequestPort createSystemCoordinateRequestPort,
            LoadSystemCoordinateRequestPort loadSystemCoordinateRequestPort,
            LoadSystemPort loadSystemPort,
            SendKafkaMessagePort sendKafkaMessagePort,
            KafkaSystemCoordinatesResponseMapper kafkaSystemCoordinatesResponseMapper,
            MessageMapper messageMapper,
            ObjectMapper objectMapper,
            @Qualifier("explorationRetryTemplate") RetryTemplate retryTemplate
    ) {
        return new ReceiveSystemCoordinateRequestService(createSystemCoordinateRequestPort, loadSystemCoordinateRequestPort, loadSystemPort, sendKafkaMessagePort, kafkaSystemCoordinatesResponseMapper, messageMapper, objectMapper, retryTemplate);
    }

    @Bean(name = "explorationLoadByNameContainingValidator")
    public LoadByNameContainingValidator loadByNameContainingValidator(
            @Value(value = "${exploration.loadbynamecontainingvalidator.min_length:3}") final int minLength,
            @Value(value = "${exploration.loadbynamecontainingvalidator.min_size:1}") final int minSize,
            @Value(value = "${exploration.loadbynamecontainingvalidator.max_size:100}") final int maxSize
    ) {
        return new LoadByNameContainingValidator(minLength, minSize, maxSize);
    }


    @Bean(name = "explorationSystemControllerService")
    public SystemControllerService systemControllerService(
            SystemRepository systemRepository,
            LoadByNameContainingValidator loadByNameContainingValidator,
            RestSystemDtoMapper restSystemDtoMapper) {
        return new SystemControllerService(systemRepository, loadByNameContainingValidator, restSystemDtoMapper);
    }

    @Bean(name = "explorationProcessPendingSystemCoordinateRequestService")
    public ProcessPendingSystemCoordinateRequestService processPendingSystemCoordinateRequestService(
            LoadAllSystemCoordinateRequestPort loadAllSystemCoordinateRequestPort,
            LoadSystemPort loadSystemPort,
            SendKafkaMessagePort sendKafkaMessagePort,
            DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort,
            KafkaSystemCoordinatesResponseMapper kafkaSystemCoordinatesResponseMapper,
            MessageMapper messageMapper,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper,
            @Qualifier("explorationRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("explorationThreadPoolTaskExecutor") Executor executor
    ) {
        return new ProcessPendingSystemCoordinateRequestService(loadAllSystemCoordinateRequestPort, loadSystemPort, sendKafkaMessagePort, deleteSystemCoordinateRequestPort, kafkaSystemCoordinatesResponseMapper, messageMapper, objectMapper, retryTemplate, executor);
    }
}
