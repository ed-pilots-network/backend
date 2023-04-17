package io.eddb.eddb2backend.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.eddb.eddb2backend.application.service.GetStationService;
import io.eddb.eddb2backend.application.usecase.GetStationUsecase;
import io.eddb.eddb2backend.domain.repository.StationRepository;
import io.eddb.eddb2backend.infrastructure.adapter.StationRepositoryAdapter;
import io.eddb.eddb2backend.infrastructure.zmq.EddnMessageHandler;
import io.eddb.eddb2backend.infrastructure.kafka.KafkaTopicHandler;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.PostgresStationRepository;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class BeanConfig {
    @Bean
    public StationRepository stationRepository(PostgresStationRepository postgresqlPostgresStationRepository) {
        return new StationRepositoryAdapter(postgresqlPostgresStationRepository);
    }

    @Bean
    public GetStationService getStationService(io.eddb.eddb2backend.domain.repository.StationRepository stationRepository) {
        return new GetStationService(stationRepository);
    }

    @Bean
    public GetStationUsecase getStationUsecase(GetStationService getStationService) {
        return getStationService;
    }

    @Bean
    public EddnMessageHandler eddnMessageHandler(@Qualifier("eddnTaskExecutor") TaskExecutor taskExecutor,
                                                 @Qualifier("eddnRetryTemplate") RetryTemplate retryTemplate,
                                                 ObjectMapper objectMapper,
                                                 KafkaTopicHandler kafkaTopicHandler,
                                                 KafkaTemplate<String, JsonNode> jsonNodekafkaTemplate) {
        return new EddnMessageHandler(taskExecutor, retryTemplate, objectMapper, kafkaTopicHandler, jsonNodekafkaTemplate);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public KafkaTopicHandler kafkaTopicCreator(AdminClient adminClient,
                                               @Value(value = "${spring.kafka.topic.partitions:1}") final int topicPartitions,
                                               @Value(value = "${spring.kafka.topic.replicationfactor:1}") final short topicReplicationFactor) {
        return new KafkaTopicHandler(adminClient, topicPartitions, topicReplicationFactor);
    }
}
