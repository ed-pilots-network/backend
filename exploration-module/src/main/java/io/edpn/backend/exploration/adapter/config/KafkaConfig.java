package io.edpn.backend.exploration.adapter.config;

import com.fasterxml.jackson.databind.JsonNode;
import io.edpn.backend.exploration.adapter.kafka.KafkaTopicHandler;
import io.edpn.backend.exploration.adapter.kafka.processor.JournalDockedV1MessageProcessor;
import io.edpn.backend.exploration.adapter.kafka.processor.JournalScanV1MessageProcessor;
import io.edpn.backend.exploration.adapter.kafka.processor.NavRouteV1MessageProcessor;
import io.edpn.backend.exploration.adapter.kafka.processor.StationDataRequestMessageProcessor;
import io.edpn.backend.exploration.adapter.kafka.processor.SystemDataRequestMessageProcessor;
import io.edpn.backend.util.Topic;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.ExponentialBackOff;

@Configuration("ExplorationModuleKafkaConfig")
public interface KafkaConfig {

    @EnableKafka
    @Configuration("ExplorationModuleEddnJsonKafkaConsumerConfig")
    @Slf4j
    class EddnJsonKafkaConsumerConfig {

        @Value(value = "${exploration.spring.kafka.bootstrap-servers}")
        private String bootstrapServers;

        public ConcurrentKafkaListenerContainerFactory<String, JsonNode> kafkaListenerContainerFactory(String groupId, CommonErrorHandler errorHandler) {
            ConcurrentKafkaListenerContainerFactory<String, JsonNode> factory = new ConcurrentKafkaListenerContainerFactory<>();
            factory.setCommonErrorHandler(errorHandler);
            factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
            factory.setConsumerFactory(consumerFactory(groupId));
            return factory;
        }

        public ConsumerFactory<String, JsonNode> consumerFactory(String groupId) {
            Map<String, Object> configProps = new HashMap<>();
            configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
            configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
            configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, JsonNode.class);
            configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
            return new DefaultKafkaConsumerFactory<>(configProps);
        }

        @Bean(name = "explorationModuleKafkaListenerContainerFactory")
        public ConcurrentKafkaListenerContainerFactory<String, JsonNode> kafkaListenerContainerFactory(EddnJsonKafkaConsumerConfig kafkaConfig, @Qualifier("explorationKafkaErrorHandler") CommonErrorHandler errorHandler) {
            return kafkaConfig.kafkaListenerContainerFactory("explorationModule", errorHandler);
        }

        @Bean(name = "explorationKafkaErrorHandler")
        public DefaultErrorHandler errorHandler(
                @Value(value = "${exploration.kafka.backoff.interval:1000}") final int interval,
                @Value(value = "${exploration.kafka.backoff.multiplier:2000}") final int multiplier,
                @Value(value = "${exploration.kafka.backoff.max_interval:32000}") final int maxInterval
        ) {
            ExponentialBackOff backOff = new ExponentialBackOff();
            backOff.setInitialInterval(interval);
            backOff.setMultiplier(multiplier);
            backOff.setMaxInterval(maxInterval);
            return new DefaultErrorHandler((consumerRecord, exception) -> {
                // TODO should there be extra logic to execute when all the retry attempts are exhausted, or do we just drop the message?
                log.error("A kafka message from topic {} could not be processed after multiple retries: {}", consumerRecord.topic(), consumerRecord.value(), exception);
            }, backOff);
        }
    }

    @Configuration("ExplorationModuleJsonNodeKafkaProducerConfig")
    class JsonNodeKafkaProducerConfig {
        @Value(value = "${exploration.spring.kafka.bootstrap-servers}")
        private String bootstrapServers;

        @Bean(name = "explorationJsonNodeProducerFactory")
        public ProducerFactory<String, JsonNode> jsonNodeProducerFactory() {
            Map<String, Object> configProps = new HashMap<>();
            configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
            return new DefaultKafkaProducerFactory<>(configProps);
        }

        @Bean(name = "explorationJsonNodekafkaTemplate")
        public KafkaTemplate<String, JsonNode> jsonNodekafkaTemplate(@Qualifier("explorationJsonNodeProducerFactory") ProducerFactory<String, JsonNode> jsonNodeProducerFactory) {
            return new KafkaTemplate<>(jsonNodeProducerFactory);
        }
    }

    @Configuration("ExplorationModuleKafkaAdminConfig")
    class KafkaAdminConfig {
        @Value(value = "${exploration.spring.kafka.bootstrap-servers}")
        private String bootstrapServers;

        @Bean(name = "explorationKafkaAdminClient")
        public AdminClient kafkaAdminClient() {
            Map<String, Object> configs = new HashMap<>();
            configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            return AdminClient.create(configs);
        }

        @Bean(name = "explorationKafkaTopicCreator")
        public KafkaTopicHandler kafkaTopicCreator(@Qualifier("explorationKafkaAdminClient") AdminClient adminClient,
                                                   @Value(value = "${exploration.spring.kafka.topic.partitions:1}") final int topicPartitions,
                                                   @Value(value = "${exploration.spring.kafka.topic.replicationfactor:1}") final short topicReplicationFactor) {
            return new KafkaTopicHandler(adminClient, topicPartitions, topicReplicationFactor);
        }
    }


    @Configuration("ExplorationModuleKafkaListenerConfig")
    @Slf4j
    class KafkaListenerConfig {

        @Bean(name = "explorationSystemEliteIdRequestListener")
        public ConcurrentMessageListenerContainer<String, JsonNode> systemEliteIdRequestListener(
                @Qualifier("explorationModuleKafkaListenerContainerFactory") ConcurrentKafkaListenerContainerFactory<String, JsonNode> kafkaListenerContainerFactory,
                @Qualifier("explorationSystemEliteIdRequestMessageProcessor") SystemDataRequestMessageProcessor processor
        ) {
            String topicName = Topic.Request.SYSTEM_ELITE_ID.getTopicName();
            ContainerProperties containerProps = new ContainerProperties(topicName);
            containerProps.setMessageListener(processor);

            ConcurrentMessageListenerContainer<String, JsonNode> container = new ConcurrentMessageListenerContainer<>(
                    kafkaListenerContainerFactory.getConsumerFactory(),
                    containerProps
            );

            container.setBeanName("systemEliteIdRequestListenerContainer");
            return container;
        }

        @Bean(name = "explorationSystemCoordinatesRequestListener")
        public ConcurrentMessageListenerContainer<String, JsonNode> systemCoordinatesRequestListener(
                @Qualifier("explorationModuleKafkaListenerContainerFactory") ConcurrentKafkaListenerContainerFactory<String, JsonNode> kafkaListenerContainerFactory,
                @Qualifier("explorationSystemCoordinatesRequestMessageProcessor") SystemDataRequestMessageProcessor processor
        ) {
            String topicName = Topic.Request.SYSTEM_COORDINATES.getTopicName();
            ContainerProperties containerProps = new ContainerProperties(topicName);
            containerProps.setMessageListener(processor);

            ConcurrentMessageListenerContainer<String, JsonNode> container = new ConcurrentMessageListenerContainer<>(
                    kafkaListenerContainerFactory.getConsumerFactory(),
                    containerProps
            );

            container.setBeanName("systemCoordinatesRequestListenerContainer");
            return container;
        }
        
        @Bean(name = "explorationStationArrivalDistanceRequestListener")
        public ConcurrentMessageListenerContainer<String, JsonNode> stationArrivalDistanceRequestListener(
                @Qualifier("explorationModuleKafkaListenerContainerFactory") ConcurrentKafkaListenerContainerFactory<String, JsonNode> kafkaListenerContainerFactory,
                @Qualifier("explorationStationArrivalDistanceRequestMessageProcessor") StationDataRequestMessageProcessor processor
        ) {
            String topicName = Topic.Request.STATION_ARRIVAL_DISTANCE.getTopicName();
            ContainerProperties containerProperties = new ContainerProperties(topicName);
            containerProperties.setMessageListener(processor);
            
            ConcurrentMessageListenerContainer<String, JsonNode> container = new ConcurrentMessageListenerContainer<>(
                    kafkaListenerContainerFactory.getConsumerFactory(),
                    containerProperties
            );
            
            container.setBeanName("stationArrivalDistanceRequestListenerContainer");
            return container;
        }

        @Bean(name = "explorationStationMaxLandingPadSizeRequestListener")
        public ConcurrentMessageListenerContainer<String, JsonNode> stationMaxLandingPadSizeRequestListener(
                @Qualifier("explorationModuleKafkaListenerContainerFactory") ConcurrentKafkaListenerContainerFactory<String, JsonNode> kafkaListenerContainerFactory,
                @Qualifier("explorationStationMaxLandingPadSizeRequestMessageProcessor") StationDataRequestMessageProcessor processor
        ) {
            String topicName = Topic.Request.STATION_MAX_LANDING_PAD_SIZE.getTopicName();
            ContainerProperties containerProps = new ContainerProperties(topicName);
            containerProps.setMessageListener(processor);

            ConcurrentMessageListenerContainer<String, JsonNode> container = new ConcurrentMessageListenerContainer<>(
                    kafkaListenerContainerFactory.getConsumerFactory(),
                    containerProps
            );

            container.setBeanName("stationMaxLandingPadSizeRequestListenerContainer");
            return container;
        }

        @Bean(name = "explorationJournalV1ScanListener")
        public ConcurrentMessageListenerContainer<String, JsonNode> journalV1ScanListener(
                @Qualifier("explorationModuleKafkaListenerContainerFactory") ConcurrentKafkaListenerContainerFactory<String, JsonNode> kafkaListenerContainerFactory,
                @Qualifier("explorationJournalScanV1MessageProcessor") JournalScanV1MessageProcessor processor
        ) {
            String topicName = Topic.EDDN.JOURNAL_V1_SCAN.getTopicName();
            ContainerProperties containerProps = new ContainerProperties(topicName);
            containerProps.setMessageListener(processor);

            ConcurrentMessageListenerContainer<String, JsonNode> container = new ConcurrentMessageListenerContainer<>(
                    kafkaListenerContainerFactory.getConsumerFactory(),
                    containerProps
            );

            container.setBeanName("journalV1ScanListenerContainer");
            return container;
        }

        @Bean(name = "explorationJournalV1DockedListener")
        public ConcurrentMessageListenerContainer<String, JsonNode> journalV1DockedListener(
                @Qualifier("explorationModuleKafkaListenerContainerFactory") ConcurrentKafkaListenerContainerFactory<String, JsonNode> kafkaListenerContainerFactory,
                @Qualifier("explorationJournalDockedV1MessageProcessor") JournalDockedV1MessageProcessor processor
        ) {
            String topicName = Topic.EDDN.JOURNAL_V1_DOCKED.getTopicName();
            ContainerProperties containerProps = new ContainerProperties(topicName);
            containerProps.setMessageListener(processor);

            ConcurrentMessageListenerContainer<String, JsonNode> container = new ConcurrentMessageListenerContainer<>(
                    kafkaListenerContainerFactory.getConsumerFactory(),
                    containerProps
            );

            container.setBeanName("journalV1DockedListenerContainer");
            return container;
        }

        @Bean(name = "explorationNavRouteV1Listener")
        public ConcurrentMessageListenerContainer<String, JsonNode> navRouteV1Listener(
                @Qualifier("explorationModuleKafkaListenerContainerFactory") ConcurrentKafkaListenerContainerFactory<String, JsonNode> kafkaListenerContainerFactory,
                @Qualifier("explorationNavRouteV1MessageProcessor") NavRouteV1MessageProcessor processor
        ) {
            String topicName = Topic.EDDN.NAVROUTE_V1.getTopicName();
            ContainerProperties containerProps = new ContainerProperties(topicName);
            containerProps.setMessageListener(processor);

            ConcurrentMessageListenerContainer<String, JsonNode> container = new ConcurrentMessageListenerContainer<>(
                    kafkaListenerContainerFactory.getConsumerFactory(),
                    containerProps
            );

            container.setBeanName("navRouteV1ListenerContainer");
            return container;
        }
    }
}
