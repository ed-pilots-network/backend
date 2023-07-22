package io.edpn.backend.exploration.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.domain.repository.RequestDataMessageRepository;
import io.edpn.backend.exploration.domain.repository.SystemCoordinateDataRequestRepository;
import io.edpn.backend.exploration.domain.repository.SystemRepository;
import io.edpn.backend.exploration.infrastructure.kafka.KafkaTopicHandler;
import io.edpn.backend.exploration.infrastructure.kafka.sender.KafkaMessageSender;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.entity.RequestDataMessageMapper;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.entity.SystemCoordinateDataRequestMapper;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.entity.SystemMapper;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.mybatis.RequestDataMessageEntityMapper;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.mybatis.SystemCoordinateDataRequestEntityMapper;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.mybatis.SystemEntityMapper;
import io.edpn.backend.exploration.infrastructure.persistence.repository.MybatisSystemCoordinateDataRequestRepository;
import io.edpn.backend.exploration.infrastructure.persistence.repository.MybatisSystemRepository;
import io.edpn.backend.util.IdGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration("ExplorationModuleRepositoryConfig")
public class RepositoryConfig {

    @Bean(name = "explorationSystemRepository")
    public SystemRepository systemRepository(@Qualifier("explorationIdGenerator") IdGenerator idGenerator, SystemMapper systemMapper, SystemEntityMapper systemEntityMapper) {
        return new MybatisSystemRepository(idGenerator, systemMapper, systemEntityMapper);
    }

    @Bean(name = "explorationSystemCoordinateDataRequestRepository")
    public SystemCoordinateDataRequestRepository systemCoordinateDataRequestRepository(SystemCoordinateDataRequestMapper systemCoordinateDataRequestMapper, SystemCoordinateDataRequestEntityMapper systemCoordinateDataRequestEntityMapper) {
        return new MybatisSystemCoordinateDataRequestRepository(systemCoordinateDataRequestMapper, systemCoordinateDataRequestEntityMapper);
    }

    @Bean(name = "explorationRequestDataMessageRepository")
    public RequestDataMessageRepository requestDataMessageRepository(ObjectMapper objectMapper, RequestDataMessageEntityMapper requestDataMessageEntityMapper, RequestDataMessageMapper requestDataMessageMapper, KafkaTopicHandler kafkaTopicHandler, @Qualifier("tradeJsonNodekafkaTemplate") KafkaTemplate<String, JsonNode> jsonNodekafkaTemplate) {
        return new KafkaMessageSender(objectMapper, requestDataMessageEntityMapper, requestDataMessageMapper, kafkaTopicHandler, jsonNodekafkaTemplate);
    }
}
