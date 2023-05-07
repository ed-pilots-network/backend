package io.edpn.backend.eddnmessagelistener.configuration;


import io.edpn.backend.eddnmessagelistener.infrastructure.kafka.KafkaTopicHandler;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public AdminClient kafkaAdminClient() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return AdminClient.create(configs);
    }

    @Bean
    public KafkaTopicHandler kafkaTopicCreator(AdminClient adminClient,
                                               @Value(value = "${spring.kafka.topic.partitions:1}") final int topicPartitions,
                                               @Value(value = "${spring.kafka.topic.replicationfactor:1}") final short topicReplicationFactor) {
        return new KafkaTopicHandler(adminClient, topicPartitions, topicReplicationFactor);
    }
}
