package io.edpn.backend.trade.adapter.config;

import com.fasterxml.jackson.databind.JsonNode;
import io.edpn.backend.trade.adapter.kafka.KafkaTopicHandler;
import io.edpn.backend.trade.adapter.kafka.processor.CommodityV3MessageProcessor;
import io.edpn.backend.trade.adapter.kafka.processor.StationArrivalDistanceResponseMessageProcessor;
import io.edpn.backend.trade.adapter.kafka.processor.StationMaxLandingPadSizeResponseMessageProcessor;
import io.edpn.backend.trade.adapter.kafka.processor.StationRequireOdysseyResponseMessageProcessor;
import io.edpn.backend.trade.adapter.kafka.processor.SystemCoordinatesResponseMessageProcessor;
import io.edpn.backend.trade.adapter.kafka.processor.SystemEliteIdResponseMessageProcessor;
import io.edpn.backend.util.Module;
import io.edpn.backend.util.Topic;
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

import java.util.HashMap;
import java.util.Map;

@Configuration("TradeModuleKafkaConfig")
public interface KafkaConfig {

    @EnableKafka
    @Configuration("TradeModuleEddnJsonKafkaConsumerConfig")
    @Slf4j
    class EddnJsonKafkaConsumerConfig {

        @Value(value = "${trade.spring.kafka.bootstrap-servers}")
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

        @Bean(name = "tradeModuleKafkaListenerContainerFactory")
        public ConcurrentKafkaListenerContainerFactory<String, JsonNode> kafkaListenerContainerFactory(EddnJsonKafkaConsumerConfig kafkaConfig, @Qualifier("tradeKafkaErrorHandler") CommonErrorHandler errorHandler) {
            return kafkaConfig.kafkaListenerContainerFactory("tradeModule", errorHandler);
        }

        @Bean(name = "tradeKafkaErrorHandler")
        public DefaultErrorHandler errorHandler(
                @Value(value = "${trade.kafka.backoff.interval:1000}") final int interval,
                @Value(value = "${trade.kafka.backoff.multiplier:2000}") final int multiplier,
                @Value(value = "${trade.kafka.backoff.max_interval:32000}") final int maxInterval
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

    @Configuration("TradeModuleJsonNodeKafkaProducerConfig")
    class JsonNodeKafkaProducerConfig {
        @Value(value = "${trade.spring.kafka.bootstrap-servers}")
        private String bootstrapServers;

        @Bean(name = "tradeJsonNodeProducerFactory")
        public ProducerFactory<String, JsonNode> jsonNodeProducerFactory() {
            Map<String, Object> configProps = new HashMap<>();
            configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
            return new DefaultKafkaProducerFactory<>(configProps);
        }

        @Bean(name = "tradeJsonNodekafkaTemplate")
        public KafkaTemplate<String, JsonNode> jsonNodekafkaTemplate(@Qualifier("tradeJsonNodeProducerFactory") ProducerFactory<String, JsonNode> jsonNodeProducerFactory) {
            return new KafkaTemplate<>(jsonNodeProducerFactory);
        }
    }

    @Configuration("TradeModuleKafkaAdminConfig")
    class KafkaAdminConfig {
        @Value(value = "${trade.spring.kafka.bootstrap-servers}")
        private String bootstrapServers;

        @Bean(name = "tradeKafkaAdminClient")
        public AdminClient kafkaAdminClient() {
            Map<String, Object> configs = new HashMap<>();
            configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            return AdminClient.create(configs);
        }

        @Bean(name = "tradeKafkaTopicCreator")
        public KafkaTopicHandler kafkaTopicCreator(@Qualifier("tradeKafkaAdminClient") AdminClient adminClient,
                                                   @Value(value = "${trade.spring.kafka.topic.partitions:1}") final int topicPartitions,
                                                   @Value(value = "${trade.spring.kafka.topic.replicationfactor:1}") final short topicReplicationFactor) {
            return new KafkaTopicHandler(adminClient, topicPartitions, topicReplicationFactor);
        }
    }

    @Configuration("TradeModuleKafkaListenerConfig")
    @Slf4j
    class KafkaListenerConfig {

        @Bean(name = "tradeSystemEliteIdResponseListener")
        public ConcurrentMessageListenerContainer<String, JsonNode> systemEliteIdResponseListener(
                @Qualifier("tradeModuleKafkaListenerContainerFactory") ConcurrentKafkaListenerContainerFactory<String, JsonNode> kafkaListenerContainerFactory,
                @Qualifier("tradeSystemEliteIdResponseMessageProcessor") SystemEliteIdResponseMessageProcessor processor
        ) {
            String topicName = Topic.Response.SYSTEM_ELITE_ID.getFormattedTopicName(Module.TRADE);
            ContainerProperties containerProps = new ContainerProperties(topicName);
            containerProps.setMessageListener(processor);

            ConcurrentMessageListenerContainer<String, JsonNode> container = new ConcurrentMessageListenerContainer<>(
                    kafkaListenerContainerFactory.getConsumerFactory(),
                    containerProps
            );

            container.setBeanName("systemEliteIdResponseListenerContainer");
            return container;
        }

        @Bean(name = "tradeSystemCoordinatesResponseListener")
        public ConcurrentMessageListenerContainer<String, JsonNode> systemCoordinatesResponseListener(
                @Qualifier("tradeModuleKafkaListenerContainerFactory") ConcurrentKafkaListenerContainerFactory<String, JsonNode> kafkaListenerContainerFactory,
                @Qualifier("tradeSystemCoordinatesResponseMessageProcessor") SystemCoordinatesResponseMessageProcessor processor
        ) {
            String topicName = Topic.Response.SYSTEM_COORDINATES.getFormattedTopicName(Module.TRADE);
            ContainerProperties containerProps = new ContainerProperties(topicName);
            containerProps.setMessageListener(processor);

            ConcurrentMessageListenerContainer<String, JsonNode> container = new ConcurrentMessageListenerContainer<>(
                    kafkaListenerContainerFactory.getConsumerFactory(),
                    containerProps
            );

            container.setBeanName("systemCoordinatesResponseListenerContainer");
            return container;
        }

        @Bean(name = "tradeStationMaxLandingPadSizeResponseListener")
        public ConcurrentMessageListenerContainer<String, JsonNode> stationMaxLandingPadSizeResponseListener(
                @Qualifier("tradeModuleKafkaListenerContainerFactory") ConcurrentKafkaListenerContainerFactory<String, JsonNode> kafkaListenerContainerFactory,
                @Qualifier("tradeStationMaxLandingPadSizeResponseMessageProcessor") StationMaxLandingPadSizeResponseMessageProcessor processor
        ) {
            String topicName = Topic.Response.STATION_MAX_LANDING_PAD_SIZE.getFormattedTopicName(Module.TRADE);
            ContainerProperties containerProps = new ContainerProperties(topicName);
            containerProps.setMessageListener(processor);

            ConcurrentMessageListenerContainer<String, JsonNode> container = new ConcurrentMessageListenerContainer<>(
                    kafkaListenerContainerFactory.getConsumerFactory(),
                    containerProps
            );

            container.setBeanName("stationMaxLandingPadSizeResponseListenerContainer");
            return container;
        }

        @Bean(name = "tradeStationRequireOdysseyResponseListener")
        public ConcurrentMessageListenerContainer<String, JsonNode> stationRequireOdysseyResponseListener(
                @Qualifier("tradeModuleKafkaListenerContainerFactory") ConcurrentKafkaListenerContainerFactory<String, JsonNode> kafkaListenerContainerFactory,
                @Qualifier("tradeStationRequireOdysseyResponseMessageProcessor") StationRequireOdysseyResponseMessageProcessor processor
        ) {
            String topicName = Topic.Response.STATION_REQUIRE_ODYSSEY.getFormattedTopicName(Module.TRADE);
            ContainerProperties containerProps = new ContainerProperties(topicName);
            containerProps.setMessageListener(processor);

            ConcurrentMessageListenerContainer<String, JsonNode> container = new ConcurrentMessageListenerContainer<>(
                    kafkaListenerContainerFactory.getConsumerFactory(),
                    containerProps
            );

            container.setBeanName("stationRequireOdysseyResponseListenerContainer");
            return container;
        }

        @Bean(name = "tradeStationPlanetaryResponseListener")
        public ConcurrentMessageListenerContainer<String, JsonNode> stationPlanetaryResponseListener(
                @Qualifier("tradeModuleKafkaListenerContainerFactory") ConcurrentKafkaListenerContainerFactory<String, JsonNode> kafkaListenerContainerFactory,
                @Qualifier("tradeStationPlanetaryResponseMessageProcessor") StationRequireOdysseyResponseMessageProcessor processor
        ) {
            String topicName = Topic.Response.STATION_IS_PLANETARY.getFormattedTopicName(Module.TRADE);
            ContainerProperties containerProps = new ContainerProperties(topicName);
            containerProps.setMessageListener(processor);

            ConcurrentMessageListenerContainer<String, JsonNode> container = new ConcurrentMessageListenerContainer<>(
                    kafkaListenerContainerFactory.getConsumerFactory(),
                    containerProps
            );

            container.setBeanName("stationPlanetaryResponseListenerContainer");
            return container;
        }

        @Bean(name = "tradeStationArrivalDistanceResponseListener")
        public ConcurrentMessageListenerContainer<String, JsonNode> stationArrivalDistanceResponseListener(
                @Qualifier("tradeModuleKafkaListenerContainerFactory") ConcurrentKafkaListenerContainerFactory<String, JsonNode> kafkaListenerContainerFactory,
                @Qualifier("tradeStationArrivalDistanceResponseMessageProcessor") StationArrivalDistanceResponseMessageProcessor processor
        ) {
            String topicName = Topic.Response.STATION_ARRIVAL_DISTANCE.getFormattedTopicName(Module.TRADE);
            ContainerProperties containerProps = new ContainerProperties(topicName);
            containerProps.setMessageListener(processor);

            ConcurrentMessageListenerContainer<String, JsonNode> container = new ConcurrentMessageListenerContainer<>(
                    kafkaListenerContainerFactory.getConsumerFactory(),
                    containerProps
            );

            container.setBeanName("stationArrivalDistanceResponseListenerContainer");
            return container;
        }

        @Bean(name = "tradeCommodityV3MessageListener")
        public ConcurrentMessageListenerContainer<String, JsonNode> commodityV3MessageListener(
                @Qualifier("tradeModuleKafkaListenerContainerFactory") ConcurrentKafkaListenerContainerFactory<String, JsonNode> kafkaListenerContainerFactory,
                @Qualifier("tradeCommodityV3MessageProcessor") CommodityV3MessageProcessor processor
        ) {
            String topicName = Topic.EDDN.COMMODITY_V3.getTopicName();
            ContainerProperties containerProps = new ContainerProperties(topicName);
            containerProps.setMessageListener(processor);

            ConcurrentMessageListenerContainer<String, JsonNode> container = new ConcurrentMessageListenerContainer<>(
                    kafkaListenerContainerFactory.getConsumerFactory(),
                    containerProps
            );

            container.setBeanName("commodityV3MessageListenerContainer");
            return container;
        }
    }
}