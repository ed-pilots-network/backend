package io.edpn.backend.exploration.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.adapter.kafka.dto.mapper.KafkaSystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.adapter.kafka.dto.mapper.KafkaSystemEliteIdResponseMapper;
import io.edpn.backend.exploration.adapter.persistence.SystemRepository;
import io.edpn.backend.exploration.adapter.web.dto.mapper.RestSystemDtoMapper;
import io.edpn.backend.exploration.application.dto.mapper.MessageMapper;
import io.edpn.backend.exploration.application.port.outgoing.message.SendMessagePort;
import io.edpn.backend.exploration.application.port.outgoing.system.CreateSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.system.SaveSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.CreateSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadAllSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadSystemCoordinateRequestBySystemNamePort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.LoadAllSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.LoadSystemEliteIdRequestBySystemNamePort;
import io.edpn.backend.exploration.application.service.ProcessPendingSystemCoordinateRequestService;
import io.edpn.backend.exploration.application.service.ProcessPendingSystemEliteIdRequestService;
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
            SendMessagePort sendMessagePort,
            LoadSystemCoordinateRequestBySystemNamePort loadSystemCoordinateRequestBySystemNamePort,
            DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort,
            KafkaSystemCoordinatesResponseMapper kafkaSystemCoordinatesResponseMapper,
            LoadSystemEliteIdRequestBySystemNamePort loadSystemEliteIdRequestBySystemNamePort,
            DeleteSystemEliteIdRequestPort deleteSystemEliteIdRequestPort,
            KafkaSystemEliteIdResponseMapper kafkaSystemEliteIdResponseMapper,
            MessageMapper messageMapper,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper,
            @Qualifier("explorationRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("explorationThreadPoolTaskExecutor") Executor executor
    ) {
        return new ReceiveNavRouteService(
                createSystemPort,
                loadSystemPort,
                saveSystemPort,
                sendMessagePort,
                loadSystemCoordinateRequestBySystemNamePort,
                deleteSystemCoordinateRequestPort,
                kafkaSystemCoordinatesResponseMapper,
                loadSystemEliteIdRequestBySystemNamePort,
                deleteSystemEliteIdRequestPort,
                kafkaSystemEliteIdResponseMapper,
                messageMapper,
                objectMapper,
                retryTemplate,
                executor);
    }

    @Bean(name = "explorationReceiveSystemCoordinateRequestService")
    public ReceiveSystemCoordinateRequestService receiveSystemCoordinateRequestService(
            CreateSystemCoordinateRequestPort createSystemCoordinateRequestPort,
            LoadSystemCoordinateRequestPort loadSystemCoordinateRequestPort,
            LoadSystemPort loadSystemPort,
            SendMessagePort sendMessagePort,
            KafkaSystemCoordinatesResponseMapper kafkaSystemCoordinatesResponseMapper,
            MessageMapper messageMapper,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper,
            @Qualifier("explorationRetryTemplate") RetryTemplate retryTemplate
    ) {
        return new ReceiveSystemCoordinateRequestService(
                createSystemCoordinateRequestPort,
                loadSystemCoordinateRequestPort,
                loadSystemPort,
                sendMessagePort,
                kafkaSystemCoordinatesResponseMapper,
                messageMapper,
                objectMapper,
                retryTemplate);
    }

    @Bean(name = "explorationLoadByNameContainingValidator")
    public LoadByNameContainingValidator loadByNameContainingValidator(
            @Value(value = "${exploration.loadbynamecontainingvalidator.min_length:3}") final int minLength,
            @Value(value = "${exploration.loadbynamecontainingvalidator.min_size:1}") final int minSize,
            @Value(value = "${exploration.loadbynamecontainingvalidator.max_size:100}") final int maxSize
    ) {
        return new LoadByNameContainingValidator(
                minLength,
                minSize,
                maxSize);
    }


    @Bean(name = "explorationSystemControllerService")
    public SystemControllerService systemControllerService(
            SystemRepository systemRepository,
            LoadByNameContainingValidator loadByNameContainingValidator,
            RestSystemDtoMapper restSystemDtoMapper) {
        return new SystemControllerService(
                systemRepository,
                loadByNameContainingValidator,
                restSystemDtoMapper);
    }

    @Bean(name = "explorationProcessPendingSystemCoordinateRequestService")
    public ProcessPendingSystemCoordinateRequestService processPendingSystemCoordinateRequestService(
            LoadAllSystemCoordinateRequestPort loadAllSystemCoordinateRequestPort,
            LoadSystemPort loadSystemPort,
            SendMessagePort sendMessagePort,
            DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort,
            KafkaSystemCoordinatesResponseMapper kafkaSystemCoordinatesResponseMapper,
            MessageMapper messageMapper,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper,
            @Qualifier("explorationRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("explorationThreadPoolTaskExecutor") Executor executor
    ) {
        return new ProcessPendingSystemCoordinateRequestService(
                loadAllSystemCoordinateRequestPort,
                loadSystemPort,
                sendMessagePort,
                deleteSystemCoordinateRequestPort,
                kafkaSystemCoordinatesResponseMapper,
                messageMapper,
                objectMapper,
                retryTemplate,
                executor);
    }

    @Bean(name = "explorationProcessPendingSystemEliteIdRequestService")
    public ProcessPendingSystemEliteIdRequestService processPendingSystemEliteIdRequestService(
            LoadAllSystemEliteIdRequestPort loadAllSystemEliteIdRequestPort,
            LoadSystemPort loadSystemPort,
            SendMessagePort sendMessagePort,
            DeleteSystemEliteIdRequestPort deleteSystemEliteIdRequestPort,
            KafkaSystemEliteIdResponseMapper kafkaSystemEliteIdsResponseMapper,
            MessageMapper messageMapper,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper,
            @Qualifier("explorationRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("explorationThreadPoolTaskExecutor") Executor executor
    ) {
        return new ProcessPendingSystemEliteIdRequestService(
                loadAllSystemEliteIdRequestPort,
                loadSystemPort,
                sendMessagePort,
                deleteSystemEliteIdRequestPort,
                kafkaSystemEliteIdsResponseMapper,
                messageMapper,
                objectMapper,
                retryTemplate,
                executor);
    }
}
