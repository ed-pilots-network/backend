package io.edpn.backend.exploration.adapter.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.adapter.kafka.dto.SystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.adapter.kafka.processor.NavRouteV1MessageProcessor;
import io.edpn.backend.exploration.adapter.kafka.sender.KafkaMessageSender;
import io.edpn.backend.exploration.adapter.persistence.MybatisSystemCoordinateRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.MybatisSystemRepository;
import io.edpn.backend.exploration.adapter.persistence.SystemCoordinateRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.SystemRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.SystemCoordinateRequestEntityMapper;
import io.edpn.backend.exploration.adapter.persistence.entity.SystemEntityMapper;
import io.edpn.backend.exploration.adapter.web.dto.SystemDtoMapper;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.CreateSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.CreateTopicPort;
import io.edpn.backend.exploration.application.port.outgoing.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadAllSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemCoordinateRequestBySystemNamePort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.SaveSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.SendKafkaMessagePort;
import io.edpn.backend.exploration.application.service.ProcessPendingSystemCoordinateRequestService;
import io.edpn.backend.exploration.application.service.ReceiveNavRouteService;
import io.edpn.backend.exploration.application.service.SystemControllerService;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.NavRouteMessage;
import io.edpn.backend.util.IdGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration("ExplorationModuleBeanConfig")
public class BeanConfig {

    @Bean(name = "explorationIdGenerator")
    public IdGenerator idGenerator() {
        return new IdGenerator();
    }

    @Bean(name = "explorationObjectMapper")
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean(name = "explorationSystemControllerService")
    public SystemControllerService systemControllerService(
            SystemRepository systemRepository,
            SystemDtoMapper systemDtoMapper) {
        return new SystemControllerService(systemRepository, systemDtoMapper);
    }

    @Bean(name = "explorationSystemDtoMapper")
    public SystemDtoMapper systemDtoMapper() {
        return new SystemDtoMapper();
    }

    @Bean(name = "explorationSystemEntityMapper")
    public SystemEntityMapper systemEntityMapper() {
        return new SystemEntityMapper();
    }

    @Bean(name = "explorationSystemRepository")
    public SystemRepository systemRepository(
            MybatisSystemRepository mybatisSystemRepository,
            SystemEntityMapper systemEntityMapper,
            @Qualifier("explorationIdGenerator") IdGenerator idGenerator
    ) {
        return new SystemRepository(mybatisSystemRepository, systemEntityMapper, idGenerator);
    }

    @Bean(name = "explorationNavRouteV1MessageProcessor")
    public NavRouteV1MessageProcessor navRouteV1MessageProcessor(
            ReceiveKafkaMessageUseCase<NavRouteMessage.V1> receiveNavRouteMessageUseCase,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper
    ) {
        return new NavRouteV1MessageProcessor(receiveNavRouteMessageUseCase, objectMapper);
    }

    @Bean(name = "explorationReceiveNavRouteService")
    public ReceiveNavRouteService receiveNavRouteService(
            CreateSystemPort createSystemPort,
            LoadSystemPort loadSystemPort,
            SaveSystemPort saveSystemPort,
            SendKafkaMessagePort sendKafkaMessagePort,
            LoadSystemCoordinateRequestBySystemNamePort loadSystemCoordinateRequestBySystemNamePort,
            DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort,
            SystemCoordinatesResponseMapper systemCoordinatesResponseMapper,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper
    ) {
        return new ReceiveNavRouteService(createSystemPort, loadSystemPort, saveSystemPort, sendKafkaMessagePort, loadSystemCoordinateRequestBySystemNamePort, deleteSystemCoordinateRequestPort, systemCoordinatesResponseMapper, objectMapper);
    }

    @Bean(name = "explorationSystemCoordinatesResponseMapper")
    public SystemCoordinatesResponseMapper systemCoordinatesResponseMapper() {
        return new SystemCoordinatesResponseMapper();
    }

    @Bean(name = "explorationSystemCoordinateRequestRepository")
    public SystemCoordinateRequestRepository systemCoordinateRequestRepository(
            MybatisSystemCoordinateRequestRepository mybatisSystemCoordinateRequestRepository,
            SystemCoordinateRequestEntityMapper systemCoordinateRequestEntityMapper
    ) {
        return new SystemCoordinateRequestRepository(mybatisSystemCoordinateRequestRepository, systemCoordinateRequestEntityMapper);
    }

    @Bean(name = "explorationSystemCoordinateRequestEntityMapper")
    public SystemCoordinateRequestEntityMapper systemCoordinateRequestEntityMapper() {
        return new SystemCoordinateRequestEntityMapper();
    }

    @Bean(name = "explorationKafkaMessageSender")
    public KafkaMessageSender kafkaMessageSender(
            CreateTopicPort createTopicPort,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper,
            @Qualifier("explorationJsonNodekafkaTemplate") KafkaTemplate<String, JsonNode> jsonNodekafkaTemplate
    ) {
        return new KafkaMessageSender(createTopicPort, objectMapper, jsonNodekafkaTemplate);
    }

    @Bean(name = "explorationKafkaMessageSender")
    public ProcessPendingSystemCoordinateRequestService processPendingSystemCoordinateRequestService(
            LoadAllSystemCoordinateRequestPort loadAllSystemCoordinateRequestPort,
            LoadSystemPort loadSystemPort,
            SendKafkaMessagePort sendKafkaMessagePort,
            DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort,
            SystemCoordinatesResponseMapper systemCoordinatesResponseMapper,
            @Qualifier("explorationObjectMapper") ObjectMapper objectMapper
    ) {
        return new ProcessPendingSystemCoordinateRequestService(loadAllSystemCoordinateRequestPort, loadSystemPort, sendKafkaMessagePort, deleteSystemCoordinateRequestPort, systemCoordinatesResponseMapper, objectMapper);
    }
}
