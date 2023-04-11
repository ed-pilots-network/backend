package io.eddb.eddb2backend.configuration;

import io.eddb.eddb2backend.infrastructure.eddn.EddnMessageHandler;
import io.eddb.eddb2backend.infrastructure.eddn.dto.CommodityDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.zeromq.channel.ZeroMqChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Async;
import org.zeromq.ZContext;

@Configuration
@EnableIntegration
public class ZeromqConfig {

    @Bean
    public ZContext zContext() {
        return new ZContext();
    }

    @Bean(name = "inboundEddnChannel")
    public ZeroMqChannel inboundEddnChannel(ZContext context, @Value("${eddn.uri}") String eddnUri) {
        ZeroMqChannel channel = new ZeroMqChannel(context, false);
        channel.setConnectUrl(eddnUri);
        return channel;
    }

    @Bean
    @ServiceActivator(inputChannel = "inboundEddnChannel")
    public MessageHandler messageHandler(EddnMessageHandler eddnMessageHandler) {
        return eddnMessageHandler;
    }

}
