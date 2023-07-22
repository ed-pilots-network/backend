package io.edpn.backend.exploration.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import io.edpn.backend.exploration.infrastructure.kafka.KafkaTopicHandler;
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
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration("ExplorationModuleKafkaConfig")
public interface KafkaConfig {

    @EnableKafka
    @Configuration("ExplorationModuleEddnJsonKafkaConsumerConfig")
    class EddnJsonKafkaConsumerConfig {
        @Value(value = "${exploration.spring.kafka.bootstrap-servers}")
        private String bootstrapServers;

        public ConcurrentKafkaListenerContainerFactory<String, JsonNode> kafkaListenerContainerFactory(String groupId) {
            ConcurrentKafkaListenerContainerFactory<String, JsonNode> factory = new ConcurrentKafkaListenerContainerFactory<>();
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
        public ConcurrentKafkaListenerContainerFactory<String, JsonNode> kafkaListenerContainerFactory(EddnJsonKafkaConsumerConfig kafkaConfig) {
            return kafkaConfig.kafkaListenerContainerFactory("explorationModule");
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
}
