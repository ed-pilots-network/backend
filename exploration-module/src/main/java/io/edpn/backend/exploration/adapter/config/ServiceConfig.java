package io.edpn.backend.exploration.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.adapter.kafka.dto.mapper.KafkaSystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.adapter.kafka.dto.mapper.KafkaSystemEliteIdResponseMapper;
import io.edpn.backend.exploration.adapter.persistence.SystemRepository;
import io.edpn.backend.exploration.adapter.web.dto.mapper.RestSystemDtoMapper;
import io.edpn.backend.exploration.application.dto.web.object.mapper.MessageDtoMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemEliteIdResponseMapper;
import io.edpn.backend.exploration.application.port.outgoing.message.SendMessagePort;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.system.SaveOrUpdateSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.CreateIfNotExistsSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadAllSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadSystemCoordinateRequestBySystemNamePort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.CreateIfNotExistsSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.LoadAllSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.LoadSystemEliteIdRequestBySystemNamePort;
import io.edpn.backend.exploration.application.service.ReceiveNavRouteService;
import io.edpn.backend.exploration.application.service.SystemControllerService;
import io.edpn.backend.exploration.application.service.SystemCoordinateInterModuleCommunicationService;
import io.edpn.backend.exploration.application.service.SystemEliteIdInterModuleCommunicationService;
import io.edpn.backend.exploration.application.validation.LoadByNameContainingValidator;
import io.edpn.backend.util.IdGenerator;
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
            @Qualifier("explorationIdGenerator") IdGenerator idGenerator,
            SaveOrUpdateSystemPort saveOrUpdateSystemPort,
            SendMessagePort sendMessagePort,
            LoadSystemCoordinateRequestBySystemNamePort loadSystemCoordinateRequestBySystemNamePort,
            DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort,
            KafkaSystemCoordinatesResponseMapper kafkaSystemCoordinatesResponseMapper,
            LoadSystemEliteIdRequestBySystemNamePort loadSystemEliteIdRequestBySystemNamePort,
            DeleteSystemEliteIdRequestPort deleteSystemEliteIdRequestPort,
            KafkaSystemEliteIdResponseMapper kafkaSystemEliteIdResponseMapper,
            MessageDtoMapper messageMapper,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper,
            @Qualifier("explorationRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("explorationForkJoinPool") Executor executor
    ) {
        return new ReceiveNavRouteService(
                idGenerator,
                saveOrUpdateSystemPort,
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

    @Bean(name = "explorationSystemCoordinateInterModuleCommunicationService")
    public SystemCoordinateInterModuleCommunicationService systemCoordinateInterModuleCommunicationService(
            LoadAllSystemCoordinateRequestPort loadAllSystemCoordinateRequestPort,
            CreateIfNotExistsSystemCoordinateRequestPort createIfNotExistsSystemCoordinateRequestPort,
            DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort,
            LoadSystemPort loadSystemPort,
            SendMessagePort sendMessagePort,
            SystemCoordinatesResponseMapper systemCoordinatesResponseMapper,
            MessageDtoMapper messageMapper,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper,
            @Qualifier("explorationRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("explorationForkJoinPool") Executor executor
    ) {
        return new SystemCoordinateInterModuleCommunicationService(
                loadAllSystemCoordinateRequestPort,
                createIfNotExistsSystemCoordinateRequestPort,
                deleteSystemCoordinateRequestPort,
                loadSystemPort,
                sendMessagePort,
                systemCoordinatesResponseMapper,
                messageMapper,
                objectMapper,
                retryTemplate,
                executor
        );
    }

    @Bean(name = "explorationSystemEliteIdInterModuleCommunicationService")
    public SystemEliteIdInterModuleCommunicationService systemEliteIdInterModuleCommunicationService(
            LoadAllSystemEliteIdRequestPort loadAllSystemEliteIdRequestPort,
            CreateIfNotExistsSystemEliteIdRequestPort createIfNotExistsSystemEliteIdRequestPort,
            DeleteSystemEliteIdRequestPort deleteSystemEliteIdRequestPort,
            LoadSystemPort loadSystemPort,
            SendMessagePort sendMessagePort,
            SystemEliteIdResponseMapper systemEliteIdResponseMapper,
            MessageDtoMapper messageMapper,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper,
            @Qualifier("explorationRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("explorationForkJoinPool") Executor executor
    ) {
        return new SystemEliteIdInterModuleCommunicationService(
                loadAllSystemEliteIdRequestPort,
                createIfNotExistsSystemEliteIdRequestPort,
                deleteSystemEliteIdRequestPort,
                loadSystemPort,
                sendMessagePort,
                systemEliteIdResponseMapper,
                messageMapper,
                objectMapper,
                retryTemplate,
                executor
        );
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
}
