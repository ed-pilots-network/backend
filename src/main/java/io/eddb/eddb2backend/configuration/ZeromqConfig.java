package io.eddb.eddb2backend.configuration;

import io.eddb.eddb2backend.infrastructure.eddn.EddnMessageHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.zeromq.channel.ZeroMqChannel;
import org.zeromq.ZContext;

@Configuration
@EnableIntegration
public class ZeromqConfig {

    @Bean
    public ZContext zContext() {
        return new ZContext();
    }

    @Bean(name = "zeroMqChannel")
    public ZeroMqChannel zeroMqPubSubChannel(ZContext context, @Value("${eddn.uri}") String eddnUri) {
        ZeroMqChannel channel = new ZeroMqChannel(context, true);
        channel.setConnectUrl(eddnUri);
        return channel;
    }


    @Bean
    @ServiceActivator(inputChannel = "zeroMqChannel")
    public EddnMessageHandler eddnMessageHandler(TaskExecutor taskExecutor) {
        return new EddnMessageHandler(taskExecutor);
    }

}
